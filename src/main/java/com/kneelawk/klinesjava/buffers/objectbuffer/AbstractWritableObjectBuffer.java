package com.kneelawk.klinesjava.buffers.objectbuffer;

import java.util.Collection;

public abstract class AbstractWritableObjectBuffer<E> implements WritableObjectBuffer<E> {
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
        putElements(offset, elements);
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the chunk of elements to append to the end of this buffer.
     */
    @Override
    public void append(Collection<? extends E> elements) {
        appendBlank(elements.size());
        putElements(getSize(), elements);
    }

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the chunk of elements to prepend to the beginning of this buffer.
     */
    @Override
    public void prepend(Collection<? extends E> elements) {
        prependBlank(elements.size());
        putElements(0, elements);
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
        insertBlank(offset, elements.size());
        putElements(offset, elements);
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
        if (chunkLength > elements.size()) {
            remove(offset + elements.size(), chunkLength - elements.size());
        } else if (chunkLength < elements.size()) {
            insertBlank(offset + chunkLength, elements.size() - chunkLength);
        }

        putElements(offset, elements);
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
        long size = getSize();
        if (size > offset + elements.size()) {
            removeAfter(offset + elements.size());
        } else if (size < offset + elements.size()) {
            appendBlank(offset + elements.size() - size);
        }

        putElements(offset, elements);
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
        if (cutoff > elements.size()) {
            remove(cutoff, cutoff - elements.size());
        } else if (cutoff < elements.size()) {
            insertBlank(cutoff, elements.size() - cutoff);
        }

        putElements(0, elements);
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the new chunk of elements to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(Collection<? extends E> elements) {
        long size = getSize();
        if (size > elements.size()) {
            removeAfter(elements.size());
        } else if (size < elements.size()) {
            appendBlank(elements.size() - size);
        }

        putElements(0, elements);
    }

    /**
     * Sets the values of elements within this buffer.
     *
     * @param offset   the position in elements of the first element in this buffer to set to a new element.
     * @param elements the chunk of elements to put.
     */
    protected abstract void putElements(long offset, Collection<? extends E> elements);
}
