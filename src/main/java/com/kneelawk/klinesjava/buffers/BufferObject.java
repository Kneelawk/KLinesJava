package com.kneelawk.klinesjava.buffers;

public interface BufferObject {
    /**
     * Gets the current size of this buffer object.
     * <p>
     * This is simply the official size of this buffer, not the size of any underlying buffers this buffer might use.
     *
     * @return the current size of this buffer object.
     */
    long getSize();

    /**
     * Resizes this buffer object.
     * <p>
     * This only changes the official size of this buffer. Any effects on any underlying buffers are implementation specific.
     *
     * @param size the new size of this buffer object.
     */
    void setSize(long size);
}
