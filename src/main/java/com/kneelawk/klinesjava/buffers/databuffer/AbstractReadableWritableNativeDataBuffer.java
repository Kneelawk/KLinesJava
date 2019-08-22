package com.kneelawk.klinesjava.buffers.databuffer;

public abstract class AbstractReadableWritableNativeDataBuffer implements ReadableWritableNativeDataBuffer {

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
    public void readTo(long offset, long length, long address) {
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
     * Sets a chunk of data within this buffer to the contents of the buffer specified by length and address.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set expends beyond the current end of this buffer.
     *
     * @param offset  the position in bytes within this buffer to place the start of the new data.
     * @param length  the length in bytes of the chunk of data.
     * @param address the address of the buffer containing the new chunk of data.
     */
    @Override
    public void setNative(long offset, long length, long address) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        ensureCapacity(offset + length);

        putData(offset, length, address);

        if (offset + length > officialSize) {
            officialSize = offset + length;
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param length  the length in bytes of the chunk of data to append.
     * @param address the address of the buffer containing the chunk of data to append.
     */
    @Override
    public void appendNative(long length, long address) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        ensureCapacity(officialSize + length);

        putData(officialSize, length, address);

        officialSize += length;
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param length  the length in bytes of the chunk of data to prepend.
     * @param address the address of the buffer containing the chunk of data to prepend.
     */
    @Override
    public void prependNative(long length, long address) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        copyChunk(0, length, officialSize);

        putData(0, length, address);

        officialSize += length;
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be located.
     *
     * @param offset  the position in bytes to insert the chunk of data at.
     * @param length  the length in bytes of the chunk of data to insert.
     * @param address the address of the buffer containing the chunk of data to insert.
     */
    @Override
    public void insertNative(long offset, long length, long address) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        if (offset < officialSize) {
            copyChunk(offset, offset + length, officialSize - offset);
            putData(offset, length, address);

            officialSize += length;
        } else {
            ensureCapacity(offset + length);

            putData(offset, length, address);

            officialSize = offset + length;
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param length      the length in bytes of the chunk of bytes to replace the old chunk.
     * @param address     the address of the buffer containing the chunk of bytes to replace the old chunk.
     */
    @Override
    public void replaceNative(long offset, long chunkLength, long length, long address) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        if (offset + chunkLength < officialSize) {
            if (length != chunkLength) {
                copyChunk(offset + chunkLength, offset + length, officialSize - (offset + chunkLength));
            }

            putData(offset, length, address);

            officialSize += length - chunkLength;
        } else {
            ensureCapacity(offset + length);

            putData(offset, length, address);

            officialSize = offset + length;
        }
    }

    /**
     * Replace everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset  the index in bytes of the first byte to replace at and after.
     * @param length  the length in bytes of the chunk of data to replace the the old chunk.
     * @param address the address of the buffer containing the chunk of data to replace the old chunk.
     */
    @Override
    public void replaceAfterNative(long offset, long length, long address) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        ensureCapacity(offset + length);

        putData(offset, length, address);

        officialSize = offset + length;
    }

    /**
     * Replace everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff  the position in bytes to replace everything before.
     * @param length  the length in bytes of the new chunk of data to replace the old chunk.
     * @param address the address of the buffer containing the new chunk of data to replace the old chunk.
     */
    @Override
    public void replaceBeforeNative(long cutoff, long length, long address) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        if (length != cutoff) {
            if (cutoff < officialSize) {
                copyChunk(cutoff, length, officialSize - cutoff);
            } else {
                ensureCapacity(length);
            }
        }

        putData(0, length, address);

        if (cutoff < officialSize) {
            officialSize += length - cutoff;
        } else {
            officialSize = length;
        }
    }

    /**
     * Replace everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param length  the length of the new chunk of data to replace everything in this buffer with.
     * @param address the address of the buffer containing the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAllNative(long length, long address) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        ensureCapacity(length);

        putData(0, length, address);

        officialSize = length;
    }

    /**
     * Appends empty space to the end of this buffer.
     *
     * @param length the length in bytes of the empty space to append.
     */
    @Override
    public void appendBlank(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        ensureCapacity(officialSize + length);

        officialSize += length;
    }

    /**
     * Inserts empty space at the beginning of this buffer, moving everything in the buffer to the end of where the
     * empty space will be located.
     *
     * @param length the length in bytes of the empty space to prepend at the beginning of this buffer.
     */
    @Override
    public void prependBlank(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        copyChunk(0, length, officialSize);

        officialSize += length;
    }

    /**
     * Inserts empty space into this buffer at offset, moving the data currently after offset to the end of the new
     * empty space.
     *
     * @param offset the position in bytes to insert the empty space at.
     * @param length the length of the empty space to insert.
     */
    @Override
    public void insertBlank(long offset, long length) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }

        if (offset < officialSize) {
            copyChunk(offset, offset + length, officialSize - offset);

            officialSize += length;
        } else {
            ensureCapacity(offset + length);

            officialSize = offset + length;
        }
    }

    /**
     * Removes a chunk of data from this buffer, moving the data at the end of the removed chunk to where the removed
     * chunk began.
     *
     * @param offset      position in bytes of the chunk of data to be removed.
     * @param chunkLength the length in bytes of the chunk of data to be removed.
     */
    @Override
    public void remove(long offset, long chunkLength) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (chunkLength < 0) {
            throw new IllegalArgumentException("ChunkLength cannot be less than zero");
        }

        if (offset < officialSize) {
            if (offset + chunkLength < officialSize) {
                copyChunk(offset + chunkLength, offset, officialSize - (offset + chunkLength));

                officialSize -= chunkLength;
            } else {
                officialSize = offset;
            }
        }
    }

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in bytes of the first byte to remove at and after.
     */
    @Override
    public void removeAfter(long offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than zero");
        }

        if (offset < officialSize) {
            officialSize = offset;
        }
    }

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in bytes to remove everything before.
     */
    @Override
    public void removeBefore(long cutoff) {
        if (cutoff < 0) {
            throw new IllegalArgumentException("Cutoff cannot be less than zero");
        }

        if (cutoff < officialSize) {
            copyChunk(cutoff, 0, officialSize - cutoff);

            officialSize -= cutoff;
        } else {
            officialSize = 0;
        }
    }

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their official size to 0.
     */
    @Override
    public void clear() {
        officialSize = 0;
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
     * Copies a chunk of data from another buffer.
     *
     * @param offset  the position in bytes of the first byte within this buffer to have data copied to it.
     * @param length  the length in byte of the chunk of data to copy.
     * @param address the address of the buffer to copy from.
     */
    protected abstract void putData(long offset, long length, long address);

    /**
     * Makes sure the underlying implementation can handle data puts and gets up to capacity.
     *
     * @param capacity the highest byte that should be accessible.
     */
    protected abstract void ensureCapacity(long capacity);

    /**
     * Copies a chunk of data from one place to another within this buffer.
     * <p>
     * Note: This method also expands the buffer implementation so that it can handle copying the chunk of data to the
     * destOffset.
     *
     * @param sourceOffset the position in bytes of the first byte to be copied from.
     * @param destOffset   the position in bytes of the first byte to be copied to.
     * @param chunkLength  the length in bytes of the chunk of data to be copied.
     */
    protected abstract void copyChunk(long sourceOffset, long destOffset, long chunkLength);
}
