package com.kneelawk.klinesjava.buffers;

public interface GLArrayBufferObject {
    /**
     * Gets this buffer's OpenGL buffer name.
     *
     * @return this buffer's OpenGL buffer name.
     */
    int getId();

    /**
     * Gets this buffer's size.
     *
     * @return this buffer's size.
     */
    long getSize();
}
