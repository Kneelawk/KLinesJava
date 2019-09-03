package com.kneelawk.klinesjava.buffers.objectbuffer.data;

import com.kneelawk.klinesjava.buffers.databuffer.WritableDataBuffer;
import com.kneelawk.klinesjava.buffers.objectbuffer.AbstractWritableObjectBuffer;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;

import static org.lwjgl.system.MemoryStack.stackPush;

public abstract class AbstractWritableDataObjectBuffer<E> extends AbstractWritableObjectBuffer<E> {
    private static final int COPY_CHUNK_BYTES = 256;

    protected final WritableDataBuffer buffer;
    protected final int elementSize;
    private final int copyChunkSize;

    public AbstractWritableDataObjectBuffer(WritableDataBuffer buffer, int elementSize) {
        this(buffer, elementSize, COPY_CHUNK_BYTES / elementSize);
    }

    protected AbstractWritableDataObjectBuffer(WritableDataBuffer buffer, int elementSize, int copyChunkSize) {
        if (copyChunkSize < 1) {
            throw new IllegalArgumentException("CopyChunkSize must be at least one");
        }

        this.buffer = buffer;
        this.elementSize = elementSize;
        this.copyChunkSize = copyChunkSize;
    }

    /**
     * Sets the values of elements within this buffer.
     *
     * @param offset   the position in elements of the first element in this buffer to set to a new element.
     * @param elements the chunk of elements to put.
     */
    @Override
    protected void putElements(long offset, Collection<? extends E> elements) {
        Iterator<? extends E> it = elements.iterator();
        final int chunks = elements.size() / copyChunkSize;
        final int remainder = elements.size() % copyChunkSize;
        try (MemoryStack stack = stackPush()) {

            // allocate buffers
            ByteBuffer byteBuffer = stack.malloc(copyChunkSize * elementSize);

            // copy everything in 16-element chunks
            for (int i = 0; i < chunks; i++) {
                for (int j = 0; j < copyChunkSize; j++) {
                    E element = it.next();
                    writeElement(byteBuffer, j * elementSize, element);
                }
                buffer.set((offset + i * copyChunkSize) * elementSize, byteBuffer);
            }

            // copy the remainder elements
            if (remainder != 0) {
                byteBuffer.limit(remainder * elementSize);
                for (int i = 0; i < remainder; i++) {
                    E element = it.next();
                    writeElement(byteBuffer, i * elementSize, element);
                }
                buffer.set((offset + chunks * copyChunkSize) * elementSize, byteBuffer);
            }
        }
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
        buffer.appendBlank(length * elementSize);
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
        buffer.prependBlank(length * elementSize);
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
        buffer.insertBlank(offset * elementSize, length * elementSize);
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
        buffer.remove(offset * elementSize, chunkLength * elementSize);
    }

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in elements of the first element to remove at and after.
     */
    @Override
    public void removeAfter(long offset) {
        buffer.removeAfter(offset * elementSize);
    }

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in elements to remove everything before.
     */
    @Override
    public void removeBefore(long cutoff) {
        buffer.removeBefore(cutoff * elementSize);
    }

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their size to 0.
     */
    @Override
    public void clear() {
        buffer.clear();
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
        return buffer.getSize() / elementSize;
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
        buffer.setSize(size * elementSize);
    }

    /**
     * Used to write a single element to a buffer at a specific position.
     * <p>
     * The buffer's position and limit should be the same after this method as they were before.
     *
     * @param buffer   the buffer to write the element to.
     * @param position the position within the buffer to write the element at.
     * @param element  the element to write.
     */
    protected abstract void writeElement(ByteBuffer buffer, int position, E element);
}
