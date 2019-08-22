package com.kneelawk.klinesjava.buffers.databuffer;

public interface GLArrayDataBuffer {
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
