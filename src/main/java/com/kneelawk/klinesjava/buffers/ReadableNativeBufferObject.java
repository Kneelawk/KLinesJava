package com.kneelawk.klinesjava.buffers;

import org.lwjgl.system.CustomBuffer;

import java.nio.Buffer;

import static org.lwjgl.system.MemoryUtil.memAddress;

public interface ReadableNativeBufferObject extends ReadableBufferObject {

    /**
     * Reads a chunk of data starting at offset into the buffer represented by length and address.
     *
     * @param offset  the position in bytes of the chunk of data to read.
     * @param length  the length of the chunk of data to read.
     * @param address the address of the buffer to read the chunk of data into.
     */
    void readTo(long offset, long length, long address);

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset       the position in bytes to start reading at.
     * @param buffer       the buffer to read the chunk of data into.
     * @param elementShift the power of two that is the size of each element in the buffer.
     */
    @Override
    default void readTo(long offset, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        readTo(offset, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    default void readTo(long offset, CustomBuffer<?> buffer) {
        readTo(offset, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }
}
