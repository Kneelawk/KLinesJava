package com.kneelawk.klinesjava.buffers.objectbuffer;

import com.google.common.collect.ImmutableList;
import com.kneelawk.klinesjava.buffers.BufferObject;

import java.util.Collection;
import java.util.List;

public interface WritableObjectBuffer<E> extends BufferObject {
    /**
     * Sets a chunk of elements within this buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of elements being set extends beyond the current end of
     * this buffer.,
     *
     * @param offset   the position in elements within this buffer to place the start of the new elements.
     * @param elements the chunk of new elements to put into this buffer.
     */
    void set(long offset, Collection<? extends E> elements);

    /**
     * Sets a chunk of elements within this buffer.
     *
     * @param offset   the position in elements within this buffer to place the start of the new elements.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to set.
     */
    default void set(long offset, List<? extends E> elements, Collection<Integer> indices) {
        set(offset, indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
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
    default void set(long offset, E element) {
        set(offset, ImmutableList.of(element));
    }

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the chunk of elements to append to the end of this buffer.
     */
    void append(Collection<? extends E> elements);

    /**
     * Appends a chunk of elements to the end of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list ot draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to append.
     */
    default void append(List<? extends E> elements, Collection<Integer> indices) {
        append(indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Appends a single element to the end of this buffer.
     *
     * @param element the element to append to the end of this buffer.
     */
    default void append(E element) {
        append(ImmutableList.of(element));
    }

    /**
     * Appends a chunk of blank elements to the end of this buffer.
     * <p>
     * Note: The value of all blank elements is undefined.
     *
     * @param length the length in elements of the chunk of blank elements to append to the end of this buffer.
     */
    void appendBlank(long length);

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the chunk of elements to prepend to the beginning of this buffer.
     */
    void prepend(Collection<? extends E> elements);

    /**
     * Prepends a chunk of elements to the beginning of this buffer.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of elements to prepend.
     */
    default void prepend(List<? extends E> elements, Collection<Integer> indices) {
        prepend(indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Prepends a single element to the beginning of this buffer.
     *
     * @param element the element to prepend to the beginning of this buffer.
     */
    default void prepend(E element) {
        prepend(ImmutableList.of(element));
    }

    /**
     * Prepends a chunk of blank elements to the beginning of this buffer.
     * <p>
     * Note: The value of all blank elements is undefined.
     *
     * @param length the length in elements of the chunk of blank elements to append to the beginning of this buffer.
     */
    void prependBlank(long length);

    /**
     * Inserts a chunk of elements into this buffer at offset, moving the elements currently after offset to the end of
     * the space where the new chunk of elements will be located.
     *
     * @param offset   the position in elements to insert the chunk of elements at.
     * @param elements the chunk of elements to insert.
     */
    void insert(long offset, Collection<? extends E> elements);

    /**
     * Inserts a chunk of elements into this buffer at offset, moving the elements currently after offset to the end of
     * the space where the new chunk of elements will be located.
     *
     * @param offset   the position in elements to insert the chunk of elements at.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to insert.
     */
    default void insert(long offset, List<? extends E> elements, Collection<Integer> indices) {
        insert(offset, indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Inserts a single element into this buffer at offset, moving the elements currently after offset to the end of the
     * space where the new element will be located.
     *
     * @param offset  the position in elements to insert the chunk of elements at.
     * @param element the element to insert.
     */
    default void insert(long offset, E element) {
        insert(offset, ImmutableList.of(element));
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
    void insertBlank(long offset, long length);

    /**
     * Replaces a chunk of elements in this buffer with a new chunk of elements, moving the elements currently after the
     * old chunk of elements to the end of the space where the new chunk of elements will be located.
     *
     * @param offset      the position in elements of the chunk to be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param elements    the new chunk of elements to replace the old one.
     */
    void replace(long offset, long chunkLength, Collection<? extends E> elements);

    /**
     * Replaces a chunk of elements in this buffer with a new chunk of elements, moving the elements currently after the
     * old chunk of elements to the end of the space where the new chunk of elements will be located.
     *
     * @param offset      the position in elements of the chunk ot be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param elements    the indexed list of unique elements for the indices list to draw from.
     * @param indices     the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    default void replace(long offset, long chunkLength, List<? extends E> elements, Collection<Integer> indices) {
        replace(offset, chunkLength, indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces a chunk of elements in this buffer with a single element, moving the elements currently after the old
     * chunk of elements to the end of the space where the new element will be located.
     *
     * @param offset      the position in elements of the chunk to be replaced.
     * @param chunkLength the length in elements of the chunk to be replaced.
     * @param element     the new element to replace the old chunk of elements.
     */
    default void replace(long offset, long chunkLength, E element) {
        replace(offset, chunkLength, ImmutableList.of(element));
    }

    /**
     * Replaces everything at and after offset with a new chunk of elements, resizing this buffer so that it ends at the
     * end of the new chunk of elements.
     *
     * @param offset   the position in elements of the first element to replace.
     * @param elements the new chunk of elements to replace the old one.
     */
    void replaceAfter(long offset, Collection<? extends E> elements);

    /**
     * Replaces everything at and after offset with a new chunk of elements, resizing this buffer so that it ends at the
     * end of the new chunk of elements.
     *
     * @param offset   the position in elements of the first element to replace.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    default void replaceAfter(long offset, List<? extends E> elements, Collection<Integer> indices) {
        replaceAfter(offset, indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything at and after offset with a single element, resizing this buffer so that it ends at the end of
     * the new element.
     *
     * @param offset  the position in elements of the first element to replace.
     * @param element the new element to replace the old chunk with.
     */
    default void replaceAfter(long offset, E element) {
        replaceAfter(offset, ImmutableList.of(element));
    }

    /**
     * Replaces everything before cutoff with a new chunk of elements, moving everything after the old chunk of elements
     * to the end of the space where the new chunk of elements will be located.
     *
     * @param cutoff   the position in elements to replace everything before.
     * @param elements the new chunk of elements to replace the old one.
     */
    void replaceBefore(long cutoff, Collection<? extends E> elements);

    /**
     * Replaces everything before cutoff with a new chunk of elements, moving everything after the old chunk of elements
     * to the end of the space where the new chunk of elements will be located.
     *
     * @param cutoff   the position in elements to replace everything before.
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    default void replaceBefore(long cutoff, List<? extends E> elements, Collection<Integer> indices) {
        replaceBefore(cutoff, indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything before cutoff with a single element, moving everything after the old chunk of elements to the
     * end of the space where the new element will be located.
     *
     * @param cutoff  the position in elements to replace everything before.
     * @param element the new element to replace the old chunk of elements.
     */
    default void replaceBefore(long cutoff, E element) {
        replaceBefore(cutoff, ImmutableList.of(element));
    }

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the new chunk of elements to replace everything in this buffer with.
     */
    void replaceAll(Collection<? extends E> elements);

    /**
     * Replaces everything in this buffer with a new chunk of elements, resizing this buffer to match the size of the
     * new chunk of elements.
     *
     * @param elements the indexed list of unique elements for the indices list to draw from.
     * @param indices  the list of indices into the elements list that makes up the chunk of data to replace with.
     */
    default void replaceAll(List<? extends E> elements, Collection<Integer> indices) {
        replaceAll(indices.stream().map(elements::get).collect(ImmutableList.toImmutableList()));
    }

    /**
     * Replaces everything in this buffer with a single element, resizing this buffer to match the size of the new
     * element.
     *
     * @param element the element to replace everything in this buffer with.
     */
    default void replaceAll(E element) {
        replaceAll(ImmutableList.of(element));
    }

    /**
     * Removes a chunk of elements from this buffer, moving the elements at the end of the removed chunk to where the
     * removed chunk began.
     *
     * @param offset      the position in elements of the chunk to be removed.
     * @param chunkLength the length in elements of the chunk to be removed.
     */
    void remove(long offset, long chunkLength);

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in elements of the first element to remove at and after.
     */
    void removeAfter(long offset);

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in elements to remove everything before.
     */
    void removeBefore(long cutoff);

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their size to 0.
     */
    void clear();
}
