package com.kneelawk.klinesjava.buffers.databuffer;

public abstract class AbstractReadableNativeDataBuffer implements ReadableNativeDataBuffer {

    /**
     * The official size of this buffer object.
     */
    protected long officialSize = 0;

    /**
     * Gets the current size of this buffer object.
     * <p>
     * This is simply the official size of this buffer, not the size of any underlying buffers this buffer might use.
     *
     * @return the current size of this buffer object.
     */
    @Override
    public long getSize() {
        return officialSize;
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

        ensureCapacity(size);
        officialSize = size;
    }

    /**
     * Reads a chunk of data starting at offset into the buffer represented by length and address.
     *
     * @param offset  the position in bytes of the chunk of data to read.
     * @param length  the length of the chunk of data to read.
     * @param address the address of the buffer to read the chunk of data into.
     */
    @Override
    public void readToNative(long offset, long length, long address) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        if (offset + length > officialSize) {
            throw new IndexOutOfBoundsException(
                    "offset + length > size (" + offset + " + " + length + " > " + officialSize + ")");
        }

        getData(offset, length, address);
    }

    /**
     * Copies a chunk of data to another buffer.
     *
     * @param offset  the position in bytes of the first byte within this buffer to copy.
     * @param length  the length in bytes of the chunk of data to copy.
     * @param address the address of the buffer to copy to.
     */
    protected abstract void getData(long offset, long length, long address);

    /**
     * Makes sure the underlying implementation can handle data puts and gets up to capacity.
     *
     * @param capacity the highest byte that should be accessible.
     */
    protected abstract void ensureCapacity(long capacity);
}
