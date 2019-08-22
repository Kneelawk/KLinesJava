package com.kneelawk.klinesjava.buffers.databuffer;

import com.kneelawk.klinesjava.buffers.BufferObject;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;

import java.nio.*;

import static com.kneelawk.klinesjava.buffers.ElementShifts.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public interface WritableDataBuffer extends BufferObject {

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset       the position in bytes within this buffer to place the start of the new data.
     * @param buffer       the chunk of new data to put into this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    void set(long offset, Buffer buffer, int elementShift);

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    void set(long offset, CustomBuffer<?> buffer);

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, ByteBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Sets a single byte within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the byte to set.
     * @param value  the new value of the byte being set.
     */
    default void set(long offset, byte value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, ShortBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Sets a single short within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the short to set.
     * @param value  the new value of the short being set.
     */
    default void set(long offset, short value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, IntBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Sets a single int within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the int to set.
     * @param value  the new value of the int being set.
     */
    default void set(long offset, int value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, LongBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Sets a single long within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the long to set.
     * @param value  the new value of the long being set.
     */
    default void set(long offset, long value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, FloatBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Sets a single float within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the float to set.
     * @param value  the new value of the float being set.
     */
    default void set(long offset, float value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    default void set(long offset, DoubleBuffer buffer) {
        set(offset, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Sets a single double within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the double to set.
     * @param value  the new value of the double being set.
     */
    default void set(long offset, double value) {
        try (MemoryStack stack = stackPush()) {
            set(offset, stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer       the chunk of data to append to the end of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    void append(Buffer buffer, int elementShift);

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    void append(CustomBuffer<?> buffer);

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(ByteBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Appends a single byte to the end of this buffer.
     *
     * @param value the value of the byte appended to the end of this buffer.
     */
    default void append(byte value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(ShortBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Appends a single short to the end of this buffer.
     *
     * @param value the value of the short appended to the end of this buffer.
     */
    default void append(short value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(IntBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Appends a single int to the end of this buffer.
     *
     * @param value the value of the int appended to the end of this buffer.
     */
    default void append(int value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(LongBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Appends a single long to the end of this buffer.
     *
     * @param value the value of the long appended to the end of this buffer.
     */
    default void append(long value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(FloatBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Appends a single float to the end of this buffer.
     *
     * @param value the value of the float appended to the end of this buffer.
     */
    default void append(float value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    default void append(DoubleBuffer buffer) {
        append(buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Appends a single double to the end of this buffer.
     *
     * @param value the value of the double appended to the end of this buffer.
     */
    default void append(double value) {
        try (MemoryStack stack = stackPush()) {
            append(stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Appends empty space to the end of this buffer.
     *
     * @param length the length in bytes of the empty space to append.
     */
    void appendBlank(long length);

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer       the chunk of data to prepend at the beginning of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    void prepend(Buffer buffer, int elementShift);

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    void prepend(CustomBuffer<?> buffer);

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(ByteBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Inserts a single byte at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new byte will be located.
     *
     * @param value the value of the byte to prepend at the beginning of this buffer.
     */
    default void prepend(byte value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(ShortBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Inserts a single short at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new short will be located.
     *
     * @param value the value of the short to prepend at the beginning of this buffer.
     */
    default void prepend(short value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(IntBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Inserts a single int at the beginning of this buffer, moving everything in the buffer to the end of where the new
     * int will be located.
     *
     * @param value the value of the int to prepend at the beginning of this buffer.
     */
    default void prepend(int value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(LongBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Inserts a single long at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new long will be located.
     *
     * @param value the value of the long to prepend at the beginning of this buffer.
     */
    default void prepend(long value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(FloatBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Inserts a single float at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new float will be located.
     *
     * @param value the value of the float to prepend at the beginning of this buffer.
     */
    default void prepend(float value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    default void prepend(DoubleBuffer buffer) {
        prepend(buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Inserts a single double at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new double will be located.
     *
     * @param value the value of the double to prepend at the beginning of this buffer.
     */
    default void prepend(double value) {
        try (MemoryStack stack = stackPush()) {
            prepend(stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Inserts empty space at the beginning of this buffer, moving everything in the buffer to the end of where the
     * empty space will be located.
     *
     * @param length the length in bytes of the empty space to prepend at the beginning of this buffer.
     */
    void prependBlank(long length);

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be located.
     *
     * @param offset       the position in bytes to insert the chunk of data at.
     * @param buffer       the chunk of data to insert.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    void insert(long offset, Buffer buffer, int elementShift);

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    void insert(long offset, CustomBuffer<?> buffer);

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, ByteBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Inserts a single byte into this buffer at offset, moving the data currently after offset to the end of the space
     * where the byte will be inserted.
     *
     * @param offset the position in bytes to insert the byte at.
     * @param value  the value of the byte to insert.
     */
    default void insert(long offset, byte value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, ShortBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Inserts a single short into this buffer at offset, moving the data currently after offset to the end of the space
     * where the short will be inserted.
     *
     * @param offset the position in bytes to insert the short at.
     * @param value  the value of the short to insert.
     */
    default void insert(long offset, short value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, IntBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Inserts a single int into this buffer at offset, moving the data currently after offset to the end of the space
     * where the int will be inserted.
     *
     * @param offset the position in bytes to insert the int at.
     * @param value  the value of the int to insert.
     */
    default void insert(long offset, int value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, LongBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Inserts a single long into this buffer at offset, moving the data currently after offset to the end of the space
     * where the long will be inserted.
     *
     * @param offset the position in bytes to insert the long at.
     * @param value  the value of the long to insert.
     */
    default void insert(long offset, long value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, FloatBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Inserts a single float into this buffer at offset, moving the data currently after offset to the end of the space
     * where the float will be inserted.
     *
     * @param offset the position in bytes to insert the float at.
     * @param value  the value of the float to insert.
     */
    default void insert(long offset, float value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    default void insert(long offset, DoubleBuffer buffer) {
        insert(offset, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Inserts a single double into this buffer at offset, moving the data currently after offset to the end of the
     * space where the double will be inserted.
     *
     * @param offset the position in bytes to insert the double at.
     * @param value  the value of the double to insert.
     */
    default void insert(long offset, double value) {
        try (MemoryStack stack = stackPush()) {
            insert(offset, stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Inserts empty space into this buffer at offset, moving the data currently after offset to the end of the new
     * empty space.
     *
     * @param offset the position in bytes to insert the empty space at.
     * @param length the length of the empty space to insert.
     */
    void insertBlank(long offset, long length);

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset       the position in bytes of the chunk to be replaced.
     * @param chunkLength  the length in bytes of the chunk to be replaced.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    void replace(long offset, long chunkLength, Buffer buffer, int elementShift);

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    void replace(long offset, long chunkLength, CustomBuffer<?> buffer);

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, ByteBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Replaces a chunk of data in this buffer with a single byte, moving the data currently after the old chunk of data
     * to the end of the space where the new byte will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new byte to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, byte value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, ShortBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Replaces a chunk of data in this buffer with a single short, moving the data currently after the old chunk of
     * data to the end of the space where the new short will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new short to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, short value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, IntBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Replaces a chunk of data in this buffer with a single int, moving the data currently after the old chunk of data
     * to the end of the space where the new int will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new int to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, int value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, LongBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Replaces a chunk of data in this buffer with a single long, moving the data currently after the old chunk of data
     * to the end of the space where the new long will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new long to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, long value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, FloatBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Replaces a chunk of data in this buffer with a single float, moving the data currently after the old chunk of
     * data to the end of the space where the new float will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new float to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, float value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    default void replace(long offset, long chunkLength, DoubleBuffer buffer) {
        replace(offset, chunkLength, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Replaces a chunk of data in this buffer with a single double, moving the data currently after the old chunk of
     * data to the end of the space where the new double will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new double to replace the old chunk of data.
     */
    default void replace(long offset, long chunkLength, double value) {
        try (MemoryStack stack = stackPush()) {
            replace(offset, chunkLength, stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset       the index in bytes of the first byte to replace at and after.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    void replaceAfter(long offset, Buffer buffer, int elementShift);

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    void replaceAfter(long offset, CustomBuffer<?> buffer);

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, ByteBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Replaces everything after offset with a single byte, resizing this buffer so that it ends at the end of the new
     * byte.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new byte to replace the old chunk of data.
     */
    default void replaceAfter(long offset, byte value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, ShortBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Replaces everything after offset with a single short, resizing this buffer so that it ends at the end of the new
     * short.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new short to replace the old chunk of data.
     */
    default void replaceAfter(long offset, short value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, IntBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Replaces everything after offset with a single int, resizing this buffer so that it ends at the end of the new
     * int.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new int to replace the old chunk of data.
     */
    default void replaceAfter(long offset, int value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, LongBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Replaces everything after offset with a single long, resizing this buffer so that it ends at the end of the new
     * long.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new long to replace the old chunk of data.
     */
    default void replaceAfter(long offset, long value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, FloatBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Replaces everything after offset with a single float, resizing this buffer so that it ends at the end of the new
     * float.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new byte to replace the old chunk of data.
     */
    default void replaceAfter(long offset, float value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceAfter(long offset, DoubleBuffer buffer) {
        replaceAfter(offset, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Replaces everything after offset with a single double, resizing this buffer so that it ends at the end of the new
     * double.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new double to replace the old chunk of data.
     */
    default void replaceAfter(long offset, double value) {
        try (MemoryStack stack = stackPush()) {
            replaceAfter(offset, stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff       the position in bytes to replace everything before.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    void replaceBefore(long cutoff, Buffer buffer, int elementShift);

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    void replaceBefore(long cutoff, CustomBuffer<?> buffer);

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, ByteBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Replaces everything before cutoff with a single byte, moving the data after the old chunk of data to the end of
     * the space where the new byte will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new byte the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, byte value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, ShortBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Replaces everything before cutoff with a single short, moving the data after the old chunk of data to the end of
     * the space where the new short will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new short the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, short value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, IntBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Replaces everything before cutoff with a single int, moving the data after the old chunk of data to the end of the
     * space where the new int will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new int the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, int value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, LongBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Replaces everything before cutoff with a single long, moving the data after the old chunk of data to the end of
     * the space where the new long will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new long the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, long value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, FloatBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Replaces everything before cutoff with a single float, moving the data after the old chunk of data to the end of
     * the space where the new float will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new float the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, float value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    default void replaceBefore(long cutoff, DoubleBuffer buffer) {
        replaceBefore(cutoff, buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Replaces everything before cutoff with a single double, moving the data after the old chunk of data to the end of
     * the space where the new double will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new double the replace the old chunk of data.
     */
    default void replaceBefore(long cutoff, double value) {
        try (MemoryStack stack = stackPush()) {
            replaceBefore(cutoff, stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer       the new chunk of data to replace everything in this buffer with.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    void replaceAll(Buffer buffer, int elementShift);

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    void replaceAll(CustomBuffer<?> buffer);

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(ByteBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_BYTE);
    }

    /**
     * Replaces everything in this buffer with a single byte, resizing this buffer to match.
     *
     * @param value the value of the new byte to replace everything in this buffer with.
     */
    default void replaceAll(byte value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.bytes(value), ELEMENT_SHIFT_BYTE);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(ShortBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_SHORT);
    }

    /**
     * Replaces everything in this buffer with a single short, resizing this buffer to match.
     *
     * @param value the value of the new short to replace everything in this buffer with.
     */
    default void replaceAll(short value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.shorts(value), ELEMENT_SHIFT_SHORT);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(IntBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_INT);
    }

    /**
     * Replaces everything in this buffer with a single int, resizing this buffer to match.
     *
     * @param value the value of the new int to replace everything in this buffer with.
     */
    default void replaceAll(int value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.ints(value), ELEMENT_SHIFT_INT);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(LongBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_LONG);
    }

    /**
     * Replaces everything in this buffer with a single long, resizing this buffer to match.
     *
     * @param value the value of the new long to replace everything in this buffer with.
     */
    default void replaceAll(long value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.longs(value), ELEMENT_SHIFT_LONG);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(FloatBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_FLOAT);
    }

    /**
     * Replaces everything in this buffer with a single float, resizing this buffer to match.
     *
     * @param value the value of the new float to replace everything in this buffer with.
     */
    default void replaceAll(float value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.floats(value), ELEMENT_SHIFT_FLOAT);
        }
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    default void replaceAll(DoubleBuffer buffer) {
        replaceAll(buffer, ELEMENT_SHIFT_DOUBLE);
    }

    /**
     * Replaces everything in this buffer with a single double, resizing this buffer to match.
     *
     * @param value the value of the new double to replace everything in this buffer with.
     */
    default void replaceAll(double value) {
        try (MemoryStack stack = stackPush()) {
            replaceAll(stack.doubles(value), ELEMENT_SHIFT_DOUBLE);
        }
    }

    /**
     * Removes a chunk of data from this buffer, moving the data at the end of the removed chunk to where the removed
     * chunk began.
     *
     * @param offset      position in bytes of the chunk of data to be removed.
     * @param chunkLength the length in bytes of the chunk of data to be removed.
     */
    void remove(long offset, long chunkLength);

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in bytes of the first byte to remove at and after.
     */
    void removeAfter(long offset);

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in bytes to remove everything before.
     */
    void removeBefore(long cutoff);

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their official size to 0.
     */
    void clear();
}
