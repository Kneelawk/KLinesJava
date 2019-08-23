package com.kneelawk.klinesjava.buffers.objectbuffer;

import com.kneelawk.klinesjava.buffers.BufferObject;

import java.util.Collection;

public interface ReadableObjectBuffer<E> extends BufferObject {
    /**
     * Gets a chunk of elements starting at offset.
     *
     * @param offset the position in elements of the first element returned.
     * @param length the length in elements of the chunk of elements returned.
     * @return the specified chunk of elements.
     */
    Collection<E> get(long offset, long length);

    /**
     * Gets a single element at offset.
     *
     * @param offset the position in elements of the element returned.
     * @return the specified element.
     */
    E get(long offset);
}
