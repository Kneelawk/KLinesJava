package com.kneelawk.klinesjava.graphics;

import com.google.common.collect.ImmutableList;
import com.kneelawk.klinesjava.buffers.objectbuffer.WritableObjectBuffer;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;

import java.util.Collection;
import java.util.List;

public class WritableVertexBuffer implements WritableObjectBuffer<Vertex> {
    private final WritableObjectBuffer<Vector3fc> positionBuffer;
    private final WritableObjectBuffer<Vector3fc> colorBuffer;
    private final WritableObjectBuffer<Matrix4fc> transformBuffer;

    public WritableVertexBuffer(
            WritableObjectBuffer<Vector3fc> positionBuffer,
            WritableObjectBuffer<Vector3fc> colorBuffer,
            WritableObjectBuffer<Matrix4fc> transformBuffer) {
        this.positionBuffer = positionBuffer;
        this.colorBuffer = colorBuffer;
        this.transformBuffer = transformBuffer;
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
    public void set(long offset, Collection<? extends Vertex> elements) {
        positionBuffer.set(offset, elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.set(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer
                .set(offset, elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the chunk of elements to append to the end of this buffer.
     */
    @Override
    public void append(Collection<? extends Vertex> elements) {
        positionBuffer.append(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.append(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer.append(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
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
        positionBuffer.appendBlank(length);
        colorBuffer.appendBlank(length);
        transformBuffer.appendBlank(length);
    }

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the chunk of elements to prepend to the beginning of this buffer.
     */
    @Override
    public void prepend(Collection<? extends Vertex> elements) {
        positionBuffer.prepend(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.prepend(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer.prepend(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
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
        positionBuffer.prependBlank(length);
        colorBuffer.prependBlank(length);
        transformBuffer.prependBlank(length);
    }

    /**
     * Inserts a chunk of elements into this buffer at offset, moving the elements currently after offset to the end of
     * the space where the new chunk of elements will be located.
     *
     * @param offset   the position in elements to insert the chunk of elements at.
     * @param elements the chunk of elements to insert.
     */
    @Override
    public void insert(long offset, Collection<? extends Vertex> elements) {
        positionBuffer
                .insert(offset, elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.insert(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer
                .insert(offset, elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
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
        positionBuffer.insertBlank(offset, length);
        colorBuffer.insertBlank(offset, length);
        positionBuffer.insertBlank(offset, length);
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
    public void replace(long offset, long chunkLength, Collection<? extends Vertex> elements) {
        positionBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything at and after offset with a new chunk of elements, resizing this buffer so that it ends at the
     * end of the new chunk of elements.
     *
     * @param offset   the position in elements of the first element to replace.
     * @param elements the new chunk of elements to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, Collection<? extends Vertex> elements) {
        positionBuffer.replaceAfter(offset,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer
                .replaceAfter(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer.replaceAfter(offset,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything before cutoff with a new chunk of elements, moving everything after the old chunk of elements
     * to the end of the space where the new chunk of elements will be located.
     *
     * @param cutoff   the position in elements to replace everything before.
     * @param elements the new chunk of elements to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, Collection<? extends Vertex> elements) {
        positionBuffer.replaceBefore(cutoff,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.replaceBefore(cutoff,
                elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer.replaceBefore(cutoff,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the new chunk of elements to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(Collection<? extends Vertex> elements) {
        positionBuffer.replaceAll(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()));
        colorBuffer.replaceAll(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()));
        transformBuffer
                .replaceAll(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()));
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
        positionBuffer.remove(offset, chunkLength);
        colorBuffer.remove(offset, chunkLength);
        transformBuffer.remove(offset, chunkLength);
    }

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in elements of the first element to remove at and after.
     */
    @Override
    public void removeAfter(long offset) {
        positionBuffer.removeAfter(offset);
        colorBuffer.removeAfter(offset);
        transformBuffer.removeAfter(offset);
    }

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in elements to remove everything before.
     */
    @Override
    public void removeBefore(long cutoff) {
        positionBuffer.removeBefore(cutoff);
        colorBuffer.removeBefore(cutoff);
        transformBuffer.removeBefore(cutoff);
    }

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their size to 0.
     */
    @Override
    public void clear() {
        positionBuffer.clear();
        colorBuffer.clear();
        transformBuffer.clear();
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
        return positionBuffer.getSize();
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
        positionBuffer.setSize(size);
        colorBuffer.setSize(size);
        transformBuffer.setSize(size);
    }

    /**
     * Sets a chunk of elements within this buffer.
     *
     * @param offset   the position in elements within this buffer to place the start of the new elements.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to set.
     */
    @Override
    public void set(long offset, List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer.set(offset, elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()),
                indices);
        colorBuffer
                .set(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()), indices);
        transformBuffer
                .set(offset, elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()),
                        indices);
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
    public void set(long offset, Vertex element) {
        positionBuffer.set(offset, element.getPosition());
        colorBuffer.set(offset, element.getColor());
        transformBuffer.set(offset, element.getTransform());
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list ot draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to append.
     */
    @Override
    public void append(List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer
                .append(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()), indices);
        colorBuffer.append(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()), indices);
        transformBuffer
                .append(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()), indices);
    }

    /**
     * Appends a single element to the end of this buffer.
     *
     * @param element the element to append to the end of this buffer.
     */
    @Override
    public void append(Vertex element) {
        positionBuffer.append(element.getPosition());
        colorBuffer.append(element.getColor());
        transformBuffer.append(element.getTransform());
    }

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to prepend.
     */
    @Override
    public void prepend(List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer
                .prepend(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()), indices);
        colorBuffer.prepend(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()), indices);
        transformBuffer
                .prepend(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()), indices);
    }

    /**
     * Prepends a single element to the beginning of this buffer.
     *
     * @param element the element to prepend to the beginning of this buffer.
     */
    @Override
    public void prepend(Vertex element) {
        positionBuffer.prepend(element.getPosition());
        colorBuffer.prepend(element.getColor());
        transformBuffer.prepend(element.getTransform());
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
    public void insert(long offset, List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer
                .insert(offset, elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()),
                        indices);
        colorBuffer.insert(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()),
                indices);
        transformBuffer
                .insert(offset, elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()),
                        indices);
    }

    /**
     * Inserts a single element into this buffer at offset, moving the elements currently after offset to the end of the
     * space where the new element will be located.
     *
     * @param offset  the position in elements to insert the chunk of elements at.
     * @param element the element to insert.
     */
    @Override
    public void insert(long offset, Vertex element) {
        positionBuffer.insert(offset, element.getPosition());
        colorBuffer.insert(offset, element.getColor());
        transformBuffer.insert(offset, element.getTransform());
    }

    /**
     * Replaces a chunk of elements in this buffer with a new chunk of elements, moving the elements currently after the
     * old chunk of elements to the end of the space where the new chunk of elements will be located.
     *
     * @param offset      the position in elements of the chunk ot be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param elements    the indexed list of unique elements for the indices list to draw from.
     * @param indices     the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    @Override
    public void replace(long offset, long chunkLength, List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()), indices);
        colorBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()), indices);
        transformBuffer.replace(offset, chunkLength,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()), indices);
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
    public void replace(long offset, long chunkLength, Vertex element) {
        positionBuffer.replace(offset, chunkLength, element.getPosition());
        colorBuffer.replace(offset, chunkLength, element.getColor());
        transformBuffer.replace(offset, chunkLength, element.getTransform());
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
    public void replaceAfter(long offset, List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer.replaceAfter(offset,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()), indices);
        colorBuffer
                .replaceAfter(offset, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()),
                        indices);
        transformBuffer.replaceAfter(offset,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()), indices);
    }

    /**
     * Replaces everything at and after offset with a single element, resizing this buffer so that it ends at the end of
     * the new element.
     *
     * @param offset  the position in elements of the first element to replace.
     * @param element the new element to replace the old chunk with.
     */
    @Override
    public void replaceAfter(long offset, Vertex element) {
        positionBuffer.replaceAfter(offset, element.getPosition());
        colorBuffer.replaceAfter(offset, element.getColor());
        transformBuffer.replaceAfter(offset, element.getTransform());
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
    public void replaceBefore(long cutoff, List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer.replaceBefore(cutoff,
                elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()), indices);
        colorBuffer
                .replaceBefore(cutoff, elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()),
                        indices);
        transformBuffer.replaceBefore(cutoff,
                elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()), indices);
    }

    /**
     * Replaces everything before cutoff with a single element, moving everything after the old chunk of elements to the
     * end of the space where the new element will be located.
     *
     * @param cutoff  the position in elements to replace everything before.
     * @param element the new element to replace the old chunk of elements.
     */
    @Override
    public void replaceBefore(long cutoff, Vertex element) {
        positionBuffer.replaceBefore(cutoff, element.getPosition());
        colorBuffer.replaceBefore(cutoff, element.getColor());
        transformBuffer.replaceBefore(cutoff, element.getTransform());
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    @Override
    public void replaceAll(List<? extends Vertex> elements, Collection<Integer> indices) {
        positionBuffer.replaceAll(elements.stream().map(Vertex::getPosition).collect(ImmutableList.toImmutableList()),
                indices);
        colorBuffer
                .replaceAll(elements.stream().map(Vertex::getColor).collect(ImmutableList.toImmutableList()), indices);
        transformBuffer.replaceAll(elements.stream().map(Vertex::getTransform).collect(ImmutableList.toImmutableList()),
                indices);
    }

    /**
     * Replaces everything in this buffer with a single element, resizing this buffer to match the size of the new
     * element.
     *
     * @param element the element to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(Vertex element) {
        positionBuffer.replaceAll(element.getPosition());
        colorBuffer.replaceAll(element.getColor());
        transformBuffer.replaceAll(element.getTransform());
    }
}
