package com.kneelawk.klinesjava.buffers.objectbuffer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kneelawk.klinesjava.buffers.ElementShifts;
import com.kneelawk.klinesjava.buffers.databuffer.DirectDataBuffer;
import com.kneelawk.klinesjava.buffers.databuffer.WritableDataBuffer;
import com.kneelawk.klinesjava.utils.CollectionUtils;
import org.lwjgl.system.MemoryStack;

import java.io.Closeable;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.system.MemoryStack.stackPush;

public class WritableIndexingObjectBuffer<E> implements WritableObjectBuffer<E>, Closeable {
    private static final int COPY_CHUNK_SIZE = 64;

    private WritableDataBuffer indexBuffer;
    private DirectDataBuffer readableIndexBuffer = new DirectDataBuffer();

    private WritableObjectBuffer<E> elementBuffer;
    private List<E> readableElementBuffer = Lists.newArrayList();
    // TODO: Judge performance impacts of updating every element in this list for every time a chunk of indices is moved vs updating the entire index buffer every time an element is removed
    private List<Set<Long>> elementUses = Lists.newArrayList();

    private Map<E, Integer> elementIndicesMap = Maps.newHashMap();

    public WritableIndexingObjectBuffer(WritableDataBuffer indexBuffer,
                                        WritableObjectBuffer<E> elementBuffer) {
        this.indexBuffer = indexBuffer;
        this.elementBuffer = elementBuffer;
    }

    /**
     * Sets a chunk of elements within this buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of elements being set extends beyond the current end of
     * this buffer.,
     *
     * @param offset   the position in elements within this buffer to place the start of the new elements.
     * @param elements the chunk of new elements to put into this buffer.
     */
    @Override
    public void set(long offset, Collection<? extends E> elements) {
        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        replaceIndices(offset, elements.size(), it);
    }

    /**
     * Sets a chunk of elements within this buffer.
     *
     * @param offset   the position in elements within this buffer to place the start of the new elements.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to set.
     */
    @Override
    public void set(long offset, List<? extends E> elements, Collection<Integer> indices) {
        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        replaceIndices(offset, indices.size(), it);
    }

    /**
     * Sets a single element within this buffer.
     * <p>
     * This will increase the size of this buffer if the element being set extends beyond the current end of this
     * buffer.
     *
     * @param offset  the position in elements within this buffer to place the new element.
     * @param element the new element to set in this buffer.
     */
    @Override
    public void set(long offset, E element) {
        int index = getOrAdd(element);
        replaceIndex(offset, index);
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the chunk of elements to append to the end of this buffer.
     */
    @Override
    public void append(Collection<? extends E> elements) {
        long offset = getSize();
        appendBlank(elements.size());

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        putIndices(offset, elements.size(), it);
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list ot draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to append.
     */
    @Override
    public void append(List<? extends E> elements, Collection<Integer> indices) {
        long offset = getSize();
        appendBlank(indices.size());

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        putIndices(offset, indices.size(), it);
    }

    /**
     * Appends a single element to the end of this buffer.
     *
     * @param element the element to append to the end of this buffer.
     */
    @Override
    public void append(E element) {
        long offset = getSize();
        appendBlank(1);

        int index = getOrAdd(element);
        putIndex(offset, index);
    }

    /**
     * Appends a chunk of blank elements to the end of this buffer.
     * <p>
     * Note: The value of all blank elements is undefined.
     *
     * @param length the length in elements of the chunk of blank elements to append to the end of this buffer.
     */
    @Override
    public void appendBlank(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        indexBuffer.appendBlank(length << ElementShifts.ELEMENT_SHIFT_INT);
        readableIndexBuffer.appendBlank(length << ElementShifts.ELEMENT_SHIFT_INT);
    }

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the chunk of elements to prepend to the beginning of this buffer.
     */
    @Override
    public void prepend(Collection<? extends E> elements) {
        prependBlank(elements.size());

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        putIndices(0, elements.size(), it);
    }

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to prepend.
     */
    @Override
    public void prepend(List<? extends E> elements, Collection<Integer> indices) {
        prependBlank(indices.size());

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        putIndices(0, indices.size(), it);
    }

    /**
     * Prepends a single element to the beginning of this buffer.
     *
     * @param element the element to prepend to the beginning of this buffer.
     */
    @Override
    public void prepend(E element) {
        prependBlank(1);

        int index = getOrAdd(element);
        putIndex(0, index);
    }

    /**
     * Prepends a chunk of blank elements to the beginning of this buffer.
     * <p>
     * Note: The value of all blank elements is undefined.
     *
     * @param length the length in elements of the chunk of blank elements to append to the beginning of this buffer.
     */
    @Override
    public void prependBlank(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        indexBuffer.prependBlank(length << ElementShifts.ELEMENT_SHIFT_INT);
        readableIndexBuffer.prependBlank(length << ElementShifts.ELEMENT_SHIFT_INT);

        elementUses.forEach(uses -> CollectionUtils.replaceAll(uses, position -> position + length));
    }

    /**
     * Inserts a chunk of elements into this buffer at offset, moving the elements currently after offset to the end of
     * the space where the new chunk of elements will be located.
     *
     * @param offset   the position in elements to insert the chunk of elements at.
     * @param elements the chunk of elements to insert.
     */
    @Override
    public void insert(long offset, Collection<? extends E> elements) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        insertBlank(offset, elements.size());

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        putIndices(offset, elements.size(), it);
    }

    /**
     * Inserts a chunk of elements into this buffer at offset, moving the elements currently after offset to the end of
     * the space where the new chunk of elements will be located.
     *
     * @param offset   the position in elements to insert the chunk of elements at.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to insert.
     */
    @Override
    public void insert(long offset, List<? extends E> elements, Collection<Integer> indices) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        insertBlank(offset, indices.size());

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        putIndices(offset, indices.size(), it);
    }

    /**
     * Inserts a single element into this buffer at offset, moving the elements currently after offset to the end of the
     * space where the new element will be located.
     *
     * @param offset  the position in elements to insert the chunk of elements at.
     * @param element the element to insert.
     */
    @Override
    public void insert(long offset, E element) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        insertBlank(offset, 1);

        int index = getOrAdd(element);
        putIndex(offset, index);
    }

    /**
     * Inserts a chunk of blank elements into this buffer at offset, moving the elements currently after the offset to
     * the end of the space where the empty space will be located.
     * <p>
     * Note: The value of all blank elements is undefined.
     *
     * @param offset the position in elements to insert the empty space at.
     * @param length the length in elements of the empty space to insert.
     */
    @Override
    public void insertBlank(long offset, long length) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        long size = getSize();

        indexBuffer.insertBlank(offset << ElementShifts.ELEMENT_SHIFT_INT, length << ElementShifts.ELEMENT_SHIFT_INT);
        readableIndexBuffer
                .insertBlank(offset << ElementShifts.ELEMENT_SHIFT_INT, length << ElementShifts.ELEMENT_SHIFT_INT);

        if (offset < size) {
            elementUses.forEach(
                    uses -> CollectionUtils
                            .replaceIf(uses, position -> position >= offset, position -> position + length));
        }
    }

    /**
     * Replaces a chunk of elements in this buffer with a new chunk of elements, moving the elements currently after the
     * old chunk of elements to the end of the space where the new chunk of elements will be located.
     *
     * @param offset      the position in elements of the chunk to be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param elements    the new chunk of elements to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, Collection<? extends E> elements) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        final int length = elements.size();
        if (chunkLength > length) {
            removeIndices(offset + length, chunkLength - length);
        } else if (chunkLength < length) {
            insertBlank(offset + chunkLength, length - chunkLength);
        }

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        replaceIndices(offset, length, it);
    }

    /**
     * Replaces a chunk of elements in this buffer with a new chunk of elements, moving the elements currently after the
     * old chunk of elements to the end of the space where the new chunk of elements will be located.
     *
     * @param offset      the position in elements of the chunk ot be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param elements    the indexed list of unique elements for the indices list to draw from.
     * @param indices     the list of indices into the elements list that makes up the chunk of data to insert.
     */
    @Override
    public void replace(long offset, long chunkLength, List<? extends E> elements, Collection<Integer> indices) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        final int length = indices.size();
        if (chunkLength > length) {
            removeIndices(offset + length, chunkLength - length);
        } else if (chunkLength < length) {
            insertBlank(offset + chunkLength, length - chunkLength);
        }

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        replaceIndices(offset, length, it);
    }

    /**
     * Replaces a chunk of elements in this buffer with a single element, moving the elements currently after the old
     * chunk of elements to the end of the space where the new element will be located.
     *
     * @param offset      the position in elements of the chunk to be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param element     the new element to replace the old chunk of elements.
     */
    @Override
    public void replace(long offset, long chunkLength, E element) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        if (chunkLength > 1) {
            removeIndices(offset + 1, chunkLength - 1);
        } else if (chunkLength < 1) {
            // then chunkLength must == 0
            insertBlank(offset, 1);
        }

        int index = getOrAdd(element);
        replaceIndex(offset, index);
    }

    /**
     * Replaces everything at and after offset with a new chunk of elements, resizing this buffer so that it ends at the
     * end of the new chunk of elements.
     *
     * @param offset   the position in elements of the first element to replace.
     * @param elements the new chunk of elements to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, Collection<? extends E> elements) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        final long size = getSize();
        final int length = elements.size();

        if (size > offset + length) {
            removeIndices(offset + length, size - (offset + length));
        } else if (size < offset + length) {
            appendBlank(offset + length - size);
        }

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        replaceIndices(offset, length, it);
    }

    /**
     * Replaces everything at and after offset with a new chunk of elements, resizing this buffer so that it ends at the
     * end of the new chunk of elements.
     *
     * @param offset   the position in elements of the first element to replace.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    @Override
    public void replaceAfter(long offset, List<? extends E> elements, Collection<Integer> indices) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        final long size = getSize();
        final int length = indices.size();

        if (size > offset + length) {
            removeIndices(offset + length, size - (offset + length));
        } else if (size < offset + length) {
            appendBlank(offset + length - size);
        }

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        replaceIndices(offset, length, it);
    }

    /**
     * Replaces everything at and after offset with a single element, resizing this buffer so that it ends at the end of
     * the new element.
     *
     * @param offset  the position in elements of the first element to replace.
     * @param element the new element to replace the old chunk with.
     */
    @Override
    public void replaceAfter(long offset, E element) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        final long size = getSize();

        if (size > offset + 1) {
            removeIndices(offset + 1, size - (offset + 1));
        } else if (size < offset + 1) {
            appendBlank(offset + 1 - size);
        }

        int index = getOrAdd(element);
        replaceIndex(offset, index);
    }

    /**
     * Replaces everything before cutoff with a new chunk of elements, moving everything after the old chunk of elements
     * to the end of the space where the new chunk of elements will be located.
     *
     * @param cutoff   the position in elements to replace everything before.
     * @param elements the new chunk of elements to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, Collection<? extends E> elements) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        final int length = elements.size();

        if (cutoff > length) {
            removeIndices(length, cutoff - length);
        } else if (cutoff < length) {
            insertBlank(cutoff, length - cutoff);
        }

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        replaceIndices(0, length, it);
    }

    /**
     * Replaces everything before cutoff with a new chunk of elements, moving everything after the old chunk of elements
     * to the end of the space where the new chunk of elements will be located.
     *
     * @param cutoff   the position in elements to replace everything before.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    @Override
    public void replaceBefore(long cutoff, List<? extends E> elements, Collection<Integer> indices) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        final int length = elements.size();

        if (cutoff > length) {
            removeIndices(length, cutoff - length);
        } else if (cutoff < length) {
            insertBlank(cutoff, length - cutoff);
        }

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        replaceIndices(0, length, it);
    }

    /**
     * Replaces everything before cutoff with a single element, moving everything after the old chunk of elements to the
     * end of the space where the new element will be located.
     *
     * @param cutoff  the position in elements to replace everything before.
     * @param element the new element to replace the old chunk of elements.
     */
    @Override
    public void replaceBefore(long cutoff, E element) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        if (cutoff > 1) {
            removeIndices(1, cutoff - 1);
        } else if (cutoff < 1) {
            prependBlank(1);
        }

        int index = getOrAdd(element);
        replaceIndex(0, index);
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the new chunk of elements to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(Collection<? extends E> elements) {
        final long size = getSize();
        final int length = elements.size();

        if (size > length) {
            removeIndices(length, size - length);
        } else if (size < length) {
            appendBlank(length - size);
        }

        var it = elements.stream().mapToInt(this::getOrAdd).iterator();
        replaceIndices(0, length, it);
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    @Override
    public void replaceAll(List<? extends E> elements, Collection<Integer> indices) {
        final long size = getSize();
        final int length = indices.size();

        if (size > length) {
            removeIndices(length, size - length);
        } else if (size < length) {
            appendBlank(length - size);
        }

        int[] elementRefs = elements.stream().mapToInt(this::getOrAdd).toArray();
        var it = indices.stream().mapToInt(i -> elementRefs[i]).iterator();
        replaceIndices(0, length, it);
    }

    /**
     * Replaces everything in this buffer with a single element, resizing this buffer to match the size of the new
     * element.
     *
     * @param element the element to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(E element) {
        final long size = getSize();

        if (size > 1) {
            removeIndices(1, size - 1);
        } else if (size < 1) {
            appendBlank(1);
        }

        int index = getOrAdd(element);
        replaceIndex(0, index);
    }

    /**
     * Removes a chunk of elements from this buffer, moving the elements at the end of the removed chunk to where the
     * removed chunk began.
     *
     * @param offset      the position in elements of the chunk to be removed.
     * @param chunkLength the length in elements of the chunk to be removed.
     */
    @Override
    public void remove(long offset, long chunkLength) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        removeIndices(offset, chunkLength);
    }

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in elements of the first element to remove at and after.
     */
    @Override
    public void removeAfter(long offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        long size = getSize();
        if (offset < size) {
            removeIndices(offset, getSize() - offset);
        }
    }

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in elements to remove everything before.
     */
    @Override
    public void removeBefore(long cutoff) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        long size = getSize();
        if (cutoff < size) {
            removeIndices(0, cutoff);
        } else {
            clear();
        }
    }

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their size to 0.
     */
    @Override
    public void clear() {
        indexBuffer.clear();
        readableIndexBuffer.clear();

        elementBuffer.clear();
        readableElementBuffer.clear();
        elementUses.clear();

        elementIndicesMap.clear();
    }

    /**
     * Gets the current size of this buffer object.
     * <p>
     * This is simply the official size of this buffer, not the size of any underlying buffers this buffer might use.
     *
     * @return the current size of this buffer object.
     */
    @Override
    public long getSize() {
        return readableIndexBuffer.getSize() >> ElementShifts.ELEMENT_SHIFT_INT;
    }

    /**
     * Resizes this buffer object.
     * <p>
     * This only changes the official size of this buffer. Any effects on any underlying buffers are implementation specific.
     *
     * @param size the new size of this buffer object.
     */
    @Override
    public void setSize(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be less than zero");
        }

        long currentSize = getSize();
        if (size > currentSize) {
            appendBlank(size - currentSize);
        } else if (size < currentSize) {
            removeIndices(size, currentSize - size);
        }
    }

    /**
     * Closes this BufferObject and releases its underlying buffers.
     * If the BufferObject is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
        readableIndexBuffer.close();
    }

    private void putIndices(long offset, long length, PrimitiveIterator.OfInt it) {
        final long chunks = length / COPY_CHUNK_SIZE;
        final int remainder = (int) (length % COPY_CHUNK_SIZE);

        try (MemoryStack stack = stackPush()) {

            // allocate buffer
            IntBuffer buffer = stack.mallocInt(COPY_CHUNK_SIZE);

            // copy everything in 64 int chunks
            for (int i = 0; i < chunks; i++) {
                for (int j = 0; j < COPY_CHUNK_SIZE; j++) {
                    int index = it.nextInt();
                    buffer.put(j, index);
                    elementUses.get(index).add((long) (i * COPY_CHUNK_SIZE + j));
                }

                // set the new indices
                indexBuffer.set((offset + i * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
                readableIndexBuffer.set((offset + i * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
            }

            // copy the remainder
            if (remainder != 0) {
                buffer.limit(remainder);
                for (int i = 0; i < remainder; i++) {
                    int index = it.nextInt();
                    buffer.put(i, index);
                    elementUses.get(index).add(chunks * COPY_CHUNK_SIZE + i);
                }

                // set the new indices
                indexBuffer.set((offset + chunks * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
                readableIndexBuffer.set((offset + chunks * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
            }
        }
    }

    private void putIndex(long offset, int index) {
        // set new index
        indexBuffer.set(offset << ElementShifts.ELEMENT_SHIFT_INT, index);
        readableIndexBuffer.set(offset << ElementShifts.ELEMENT_SHIFT_INT, index);
        elementUses.get(index).add(offset);
    }

    private void replaceIndices(long offset, long length, PrimitiveIterator.OfInt it) {
        final long size = getSize();
        final long chunks = length / COPY_CHUNK_SIZE;
        final int remainder = (int) (length % COPY_CHUNK_SIZE);

        try (MemoryStack stack = stackPush()) {

            // allocate buffer
            IntBuffer buffer = stack.mallocInt(COPY_CHUNK_SIZE);

            // copy everything in 64 int chunks
            for (int i = 0; i < chunks; i++) {
                for (int j = 0; j < COPY_CHUNK_SIZE; j++) {
                    int index = it.nextInt();
                    long pieceOffset = i * COPY_CHUNK_SIZE + j;
                    elementUses.get(index).add(pieceOffset);
                    if (pieceOffset < size &&
                            index != readableIndexBuffer.readInt(pieceOffset << ElementShifts.ELEMENT_SHIFT_INT)) {
                        removeIndexReference(pieceOffset);
                    }
                    buffer.put(j, index);
                }

                // set the new indices
                indexBuffer.set((offset + i * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
                readableIndexBuffer.set((offset + i * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
            }

            // copy the remainder
            if (remainder != 0) {
                buffer.limit(remainder);
                for (int i = 0; i < remainder; i++) {
                    int index = it.nextInt();
                    long pieceOffset = chunks * COPY_CHUNK_SIZE + i;
                    elementUses.get(index).add(pieceOffset);
                    if (pieceOffset < size &&
                            index != readableIndexBuffer.readInt(pieceOffset << ElementShifts.ELEMENT_SHIFT_INT)) {
                        removeIndexReference(pieceOffset);
                    }
                    buffer.put(i, index);
                }

                // set the new indices
                indexBuffer.set((offset + chunks * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
                readableIndexBuffer.set((offset + chunks * COPY_CHUNK_SIZE) << ElementShifts.ELEMENT_SHIFT_INT, buffer);
            }
        }
    }

    private void replaceIndex(long offset, int index) {
        if (index != readableIndexBuffer.readInt(offset << ElementShifts.ELEMENT_SHIFT_INT)) {
            // remove old index
            removeIndexReference(offset);

            // set new index
            indexBuffer.set(offset << ElementShifts.ELEMENT_SHIFT_INT, index);
            readableIndexBuffer.set(offset << ElementShifts.ELEMENT_SHIFT_INT, index);
            elementUses.get(index).add(offset);
        }
    }

    private int getOrAdd(E element) {
        if (elementIndicesMap.containsKey(element)) {
            return elementIndicesMap.get(element);
        } else {
            int index = readableElementBuffer.size();

            elementBuffer.append(element);
            readableElementBuffer.add(element);

            elementIndicesMap.put(element, index);

            elementUses.add(Sets.newHashSet());

            return index;
        }
    }

    private void removeIndices(long position, long length) {
        // remove from backing buffer
        indexBuffer.remove(position << ElementShifts.ELEMENT_SHIFT_INT, length << ElementShifts.ELEMENT_SHIFT_INT);

        // go through every index being removed and remove it
        for (long incrementalPosition = position; incrementalPosition < position + length; incrementalPosition++) {
            // remove all references to this index
            removeIndexReference(incrementalPosition);
        }

        // remove the whole chunk from internal buffer
        readableIndexBuffer
                .remove(position << ElementShifts.ELEMENT_SHIFT_INT, length << ElementShifts.ELEMENT_SHIFT_INT);

        // decrement all the reverse references to account for the indices shifting back in the index buffer
        elementUses.forEach(uses -> CollectionUtils
                .replaceIf(uses, laterIndex -> laterIndex >= position, laterIndex -> laterIndex - length));
    }

    private void removeIndex(long position) {
        // remove from backing buffer
        indexBuffer.remove(position << ElementShifts.ELEMENT_SHIFT_INT, ElementShifts.ELEMENT_SIZE_INT);

        // remove all references to this index
        removeIndexReference(position);

        // remove from internal buffer
        readableIndexBuffer.remove(position << ElementShifts.ELEMENT_SHIFT_INT, ElementShifts.ELEMENT_SIZE_INT);

        // decrement all reverse references to account for the indices shifting back in the index buffer
        elementUses.forEach(eachUses -> CollectionUtils
                .replaceIf(eachUses, laterIndex -> laterIndex >= position, laterIndex -> laterIndex - 1));
    }

    private void removeIndexReference(long position) {
        // get the index so we can remove things with it
        int index = readableIndexBuffer.readInt(position << ElementShifts.ELEMENT_SHIFT_INT);

        // only handle indices that are actually valid
        if (index >= 0 && index < readableElementBuffer.size()) {
            // get the number of uses decremented
            Set<Long> uses = elementUses.get(index);

            // remove this use from the collection of uses if its in there to begin with
            uses.remove(position);

            // check the number of uses
            if (uses.size() < 1) {
                // the element is no longer needed
                removeElement(index);
            }
        }
    }

    private void removeElement(int index) {
        // remove this element from the backing
        elementBuffer.remove(index, 1);
        // remove its uses value
        elementUses.remove(index);
        // get the element so we can find its association in the map
        E element = readableElementBuffer.get(index);
        // remove the element from the internal element buffer
        readableElementBuffer.remove(index);
        // remove the element from the association map
        elementIndicesMap.remove(element);

        // decrement everything pointing to indices after because the remove will have shifted everything back by 1

        // filter through elementIndicesMap and decrement any indices higher than the removed index
        for (int laterIndex = index; laterIndex < readableElementBuffer.size(); laterIndex++) {
            E laterElement = readableElementBuffer.get(laterIndex);
            elementIndicesMap.put(laterElement, laterIndex);
        }

        // collect all the indices that reference elements after the removed element and decrement them
        for (long laterReferencePosition : Iterables.concat(elementUses.subList(index, elementUses.size()))) {
            long laterReferenceBufferPosition = laterReferencePosition << ElementShifts.ELEMENT_SHIFT_INT;
            int newLaterIndex = readableIndexBuffer.readInt(laterReferenceBufferPosition) - 1;
            readableIndexBuffer.set(laterReferenceBufferPosition, newLaterIndex);
            indexBuffer.set(laterReferenceBufferPosition, newLaterIndex);
        }
    }
}
