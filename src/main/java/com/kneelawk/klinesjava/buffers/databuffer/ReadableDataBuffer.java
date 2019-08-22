package com.kneelawk.klinesjava.buffers.databuffer;

import com.kneelawk.klinesjava.buffers.BufferObject;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;

import java.nio.*;

import static com.kneelawk.klinesjava.buffers.ElementShifts.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public interface ReadableDataBuffer extends BufferObject {

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset       the position in bytes to start reading at.
     * @param buffer       the buffer to read the chunk of data into.
     * @param elementShift the power of two that is the size of each element in the buffer.
     */
    void readTo(long offset, Buffer buffer, int elementShift);

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    void readTo(long offset, CustomBuffer<?> buffer);

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, ByteBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Reads a single byte at offset from this buffer.
     *
     * @param offset the position in bytes of the byte to read.
     * @return the byte read.
     */
    default byte readByte(long offset) {
        try (MemoryStack stack = stackPush()) {
            ByteBuffer buffer = stack.malloc(1);
            readTo(offset, buffer, ELEMENT_SHIFT_BYTE);
            return buffer.get(0);
        }
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, ShortBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Reads a single short at offset from this buffer.
     *
     * @param offset the position in bytes of the short to read.
     * @return the short read.
     */
    default short readShort(long offset) {
        try (MemoryStack stack = stackPush()) {
            ShortBuffer buffer = stack.mallocShort(1);
            readTo(offset, buffer, ELEMENT_SHIFT_SHORT);
            return buffer.get(0);
        }
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, IntBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Reads a single int at offset from this buffer.
     *
     * @param offset the position in bytes of the int to read.
     * @return the int read.
     */
    default int readInt(long offset) {
        try (MemoryStack stack = stackPush()) {
            IntBuffer buffer = stack.mallocInt(1);
            readTo(offset, buffer, ELEMENT_SHIFT_INT);
            return buffer.get(0);
        }
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, LongBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Reads a single long at offset from this buffer.
     *
     * @param offset the position in bytes of the long to read.
     * @return the long read.
     */
    default long readLong(long offset) {
        try (MemoryStack stack = stackPush()) {
            LongBuffer buffer = stack.mallocLong(1);
            readTo(offset, buffer, ELEMENT_SHIFT_LONG);
            return buffer.get(0);
        }
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, FloatBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Reads a single float at offset from this buffer.
     *
     * @param offset the position in bytes of the float to read.
     * @return the float read.
     */
    default float readFloat(long offset) {
        try (MemoryStack stack = stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(1);
            readTo(offset, buffer, ELEMENT_SHIFT_FLOAT);
            return buffer.get(0);
        }
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    default void readTo(long offset, DoubleBuffer buffer) {
        readTo(offset, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Reads a single double at offset from this buffer.
     *
     * @param offset the position in bytes of the double to read.
     * @return the double read.
     */
    default double readDouble(long offset) {
        try (MemoryStack stack = stackPush()) {
            DoubleBuffer buffer = stack.mallocDouble(1);
            readTo(offset, buffer, ELEMENT_SHIFT_DOUBLE);
            return buffer.get(0);
        }
    }
}
