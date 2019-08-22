package com.kneelawk.klinesjava.buffers;

import org.lwjgl.system.MemoryStack;

import java.io.Closeable;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL43C.glInvalidateBufferData;
import static org.lwjgl.opengl.GL45C.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class WritableGLArrayBufferObject extends AbstractWritableNativeBufferObject implements GLArrayBufferObject,
        Closeable {
    private long initialCapacity;
    private long initialTmpCapacity;
    private int backing;
    private int tmpBacking;
    private long backingSize;
    private long tmpSize;

    public WritableGLArrayBufferObject() {
        this(1024);
    }

    public WritableGLArrayBufferObject(long initialCapacity) {
        this(initialCapacity, initialCapacity < 64 ? initialCapacity : initialCapacity / 2);
    }

    public WritableGLArrayBufferObject(long initialCapacity, long initialTmpCapacity) {
        this.initialCapacity = initialCapacity;
        this.initialTmpCapacity = initialTmpCapacity;

        try (MemoryStack stack = stackPush()) {
            IntBuffer bufferBuffer = stack.mallocInt(2);
            glGenBuffers(bufferBuffer);
            backing = bufferBuffer.get(0);
            tmpBacking = bufferBuffer.get(1);
        }

        glNamedBufferData(backing, initialCapacity, GL_DYNAMIC_DRAW);
        backingSize = initialCapacity;

        glNamedBufferData(tmpBacking, initialTmpCapacity, GL_DYNAMIC_COPY);
        tmpSize = initialTmpCapacity;
    }

    /**
     * Gets this buffer's OpenGL buffer name.
     *
     * @return this buffer's OpenGL buffer name.
     */
    @Override
    public int getId() {
        return backing;
    }

    /**
     * Resizes the underlying buffer to better suit the buffer's official size.
     */
    public void compact() {
        // shrink backing
        ensureTmpBacking(officialSize);

        glCopyNamedBufferSubData(backing, tmpBacking, 0, 0, officialSize);

        long newBackingSize = calculateNewSize(initialCapacity, officialSize);
        glInvalidateBufferData(backing);
        glNamedBufferData(backing, newBackingSize, GL_DYNAMIC_DRAW);
        backingSize = newBackingSize;

        glCopyNamedBufferSubData(tmpBacking, backing, 0, 0, officialSize);

        // shrink tmp backing to its initial size
        glInvalidateBufferData(tmpBacking);
        glNamedBufferData(tmpBacking, initialTmpCapacity, GL_DYNAMIC_COPY);
        tmpSize = initialTmpCapacity;
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
        nglNamedBufferSubData(backing, offset, length, address);
    }

    /**
     * Makes sure the underlying implementation can handle data puts and gets up to capacity.
     *
     * @param capacity the highest byte that should be accessible.
     */
    @Override
    protected void ensureCapacity(long capacity) {
        if (capacity > backingSize) {
            // allocate new tmp buffer if needed
            ensureTmpBacking(officialSize);

            glCopyNamedBufferSubData(backing, tmpBacking, 0, 0, officialSize);

            long newBackingSize = calculateNewSize(backingSize, capacity);
            glInvalidateBufferData(backing);
            glNamedBufferData(backing, newBackingSize, GL_DYNAMIC_DRAW);
            backingSize = newBackingSize;

            glCopyNamedBufferSubData(tmpBacking, backing, 0, 0, officialSize);
        }
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

        // allocate a new backing if needed, otherwise just copy the chunk to the tmp buffer and back again
        if (chunkEnd > backingSize) {
            // allocate new temp buffer if needed
            ensureTmpBacking(officialSize);

            glCopyNamedBufferSubData(backing, tmpBacking, 0, 0, officialSize);

            long newBackingSize = calculateNewSize(backingSize, chunkEnd);
            glInvalidateBufferData(backing);
            glNamedBufferData(backing, newBackingSize, GL_DYNAMIC_DRAW);
            backingSize = newBackingSize;

            glCopyNamedBufferSubData(tmpBacking, backing, 0, 0, officialSize);

            glCopyNamedBufferSubData(tmpBacking, backing, sourceOffset, destOffset, chunkLength);
        } else {
            ensureTmpBacking(chunkLength);

            glCopyNamedBufferSubData(backing, tmpBacking, sourceOffset, 0, chunkLength);

            glCopyNamedBufferSubData(tmpBacking, backing, 0, destOffset, chunkLength);
        }
    }

    /**
     * Closes this BufferObject and releases its underlying buffers.
     * If the BufferObject is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
        if (backing != 0) {
            try (MemoryStack stack = stackPush()) {
                glDeleteBuffers(stack.ints(backing, tmpBacking));
                backing = 0;
                tmpBacking = 0;
            }
        }
    }

    private void ensureTmpBacking(long atLeast) {
        if (tmpSize < atLeast) {
            long newTmpSize = calculateNewSize(tmpSize, atLeast);
            glInvalidateBufferData(tmpBacking);
            glNamedBufferData(tmpBacking, newTmpSize, GL_DYNAMIC_COPY);
            tmpSize = newTmpSize;
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
