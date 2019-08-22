package com.kneelawk.klinesjava.buffers;

import org.lwjgl.system.Pointer;

import java.io.Closeable;

import static org.lwjgl.system.MemoryUtil.*;

public class DirectBufferObject extends AbstractReadableWritableNativeBufferObject implements Pointer, Closeable {
    private long initialAllocation;
    private long initialTmpAllocation;
    private long backing;
    private long tmpBacking;
    private long backingSize;
    private long tmpSize;

    public DirectBufferObject() {
        this(1024);
    }

    public DirectBufferObject(long initialAllocation) {
        this(initialAllocation, initialAllocation < 64 ? initialAllocation : initialAllocation / 2);
    }

    public DirectBufferObject(long initialAllocation, long initialTmpAllocation) {
        this.initialAllocation = initialAllocation;
        this.initialTmpAllocation = initialTmpAllocation;
        backing = nmemAlloc(initialAllocation);
        tmpBacking = nmemAlloc(initialTmpAllocation);
        backingSize = initialAllocation;
        tmpSize = initialTmpAllocation;
    }

    /**
     * Returns the raw pointer address as a {@code long} value.
     *
     * @return the pointer address
     */
    @Override
    public long address() {
        return backing;
    }

    /**
     * Closes this BufferObject and releases its underlying buffers.
     * If the BufferObject is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
        if (backing != NULL) {
            nmemFree(backing);
            nmemFree(tmpBacking);
            backing = NULL;
            tmpBacking = NULL;
        }
    }

    /**
     * Resizes the underlying buffer to better suit the buffer's official size.
     */
    public void compact() {
        nmemFree(tmpBacking);
        tmpBacking = nmemAlloc(initialTmpAllocation);
        tmpSize = initialTmpAllocation;

        long newBackingSize = calculateNewSize(initialAllocation, officialSize);
        long newBacking = nmemAlloc(newBackingSize);

        memCopy(backing, newBacking, officialSize);

        long oldBacking = backing;

        backing = newBacking;
        backingSize = newBackingSize;

        nmemFree(oldBacking);
    }

    /**
     * Copies a chunk of data to another buffer.
     *
     * @param offset  the position in bytes of the first byte within this buffer to copy.
     * @param length  the length in bytes of the chunk of data to copy.
     * @param address the address of the buffer to copy to.
     */
    @Override
    protected void getData(long offset, long length, long address) {
        memCopy(backing + offset, address, length);
    }

    /**
     * Copies a chunk of data from another buffer.
     *
     * @param offset  the position in bytes of the first byte within this buffer to have data copied to it.
     * @param length  the length in byte of the chunk of data to copy.
     * @param address the address of the buffer to copy from.
     */
    @Override
    protected void putData(long offset, long length, long address) {
        memCopy(address, backing + offset, length);
    }

    /**
     * Makes sure the underlying implementation can handle data puts and gets up to capacity.
     *
     * @param capacity the highest byte that should be accessible.
     */
    @Override
    protected void ensureCapacity(long capacity) {
        if (capacity > backingSize) {
            growToAtLeast(capacity);
        }
    }

    private void growToAtLeast(long atLeast) {
        long newBackingSize = calculateNewSize(backingSize, atLeast);

        long newBacking = nmemAlloc(newBackingSize);

        // copy the data from the old backing buffer to the new backing buffer
        memCopy(backing, newBacking, officialSize);

        // remember the old backing so we can free it
        long oldBacking = backing;

        // set all our variables to point to the new backing
        backing = newBacking;
        backingSize = newBackingSize;

        // we don't need the old backing anymore
        nmemFree(oldBacking);
    }

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
    @Override
    protected void copyChunk(long sourceOffset, long destOffset, long chunkLength) {
        long chunkEnd = destOffset + chunkLength;

        // resize the backing if needed and use the old backing as the copy source,
        // otherwise use the tmp backing as the copy source
        if (backingSize < chunkEnd) {
            // allocate the new backing
            long newBackingSize = calculateNewSize(backingSize, chunkEnd);
            long newBacking = nmemAlloc(newBackingSize);

            // copy everything from the old backing
            memCopy(backing, newBacking, officialSize);

            if (chunkLength > 0) {
                // copy the chunk
                memCopy(backing + sourceOffset, newBacking + destOffset, chunkLength);
            }

            // remember the old backing so we can free it
            long oldBacking = backing;

            // update our variables
            backing = newBacking;
            backingSize = newBackingSize;

            // we don't need the old backing anymore
            nmemFree(oldBacking);
        } else if (chunkLength > 0) {
            // make sure tmpBacking is large enough
            ensureTmpBacking(chunkLength);

            // copy just the chunk to the tmp buffer.
            // we use a temporary buffer just in case a source chunk overlaps with a destination chunk
            memCopy(backing + sourceOffset, tmpBacking, chunkLength);

            // copy the chunk back from the tmp buffer
            memCopy(tmpBacking, backing + destOffset, chunkLength);
        }
    }

    private void ensureTmpBacking(long atLeast) {
        if (tmpSize < atLeast) {
            long newTempSize = calculateNewSize(tmpSize, atLeast);

            nmemFree(tmpBacking);
            tmpBacking = nmemAlloc(newTempSize);
            tmpSize = newTempSize;
        }
    }

    private long calculateNewSize(long size, long atLeast) {
        while (size < atLeast) {
            size <<= 1;

            // detect overflow
            if (size < 0) {
                size = Long.MAX_VALUE;
            }
        }
        return size;
    }
}
