package com.kneelawk.klinesjava.buffers.databuffer;

import org.lwjgl.system.CustomBuffer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.*;

/**
 * CachingWrappingDataBuffer - This buffer is used to cache data written to another buffer. Everything written to this
 * buffer is both written to an internal buffer and to the wrapped buffer. The internal buffer holds the written data so
 * that read operations can be performed.
 */
public class CachingWrappingDataBuffer implements ReadableWritableDataBuffer, Closeable {
    private WritableDataBuffer wrapped;
    private DirectDataBuffer cache;

    public CachingWrappingDataBuffer(WritableDataBuffer wrapped) {
        this.wrapped = wrapped;
        cache = new DirectDataBuffer();
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset       the position in bytes to start reading at.
     * @param buffer       the buffer to read the chunk of data into.
     * @param elementShift the power of two that is the size of each element in the buffer.
     */
    @Override
    public void readTo(long offset, Buffer buffer, int elementShift) {
        cache.readTo(offset, buffer, elementShift);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, CustomBuffer<?> buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset       the position in bytes within this buffer to place the start of the new data.
     * @param buffer       the chunk of new data to put into this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    public void set(long offset, Buffer buffer, int elementShift) {
        cache.set(offset, buffer, elementShift);
        wrapped.set(offset, buffer, elementShift);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, CustomBuffer<?> buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer       the chunk of data to append to the end of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    public void append(Buffer buffer, int elementShift) {
        cache.append(buffer, elementShift);
        wrapped.append(buffer, elementShift);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(CustomBuffer<?> buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends empty space to the end of this buffer.
     *
     * @param length the length in bytes of the empty space to append.
     */
    @Override
    public void appendBlank(long length) {
        cache.appendBlank(length);
        wrapped.appendBlank(length);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer       the chunk of data to prepend at the beginning of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    public void prepend(Buffer buffer, int elementShift) {
        cache.prepend(buffer, elementShift);
        wrapped.prepend(buffer, elementShift);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(CustomBuffer<?> buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts empty space at the beginning of this buffer, moving everything in the buffer to the end of where the
     * empty space will be located.
     *
     * @param length the length in bytes of the empty space to prepend at the beginning of this buffer.
     */
    @Override
    public void prependBlank(long length) {
        cache.prependBlank(length);
        wrapped.prepend(length);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be located.
     *
     * @param offset       the position in bytes to insert the chunk of data at.
     * @param buffer       the chunk of data to insert.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    public void insert(long offset, Buffer buffer, int elementShift) {
        cache.insert(offset, buffer, elementShift);
        wrapped.insert(offset, buffer, elementShift);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, CustomBuffer<?> buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
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
        cache.insertBlank(offset, length);
        wrapped.insertBlank(offset, length);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset       the position in bytes of the chunk to be replaced.
     * @param chunkLength  the length in bytes of the chunk to be replaced.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, Buffer buffer, int elementShift) {
        cache.replace(offset, chunkLength, buffer, elementShift);
        wrapped.replace(offset, chunkLength, buffer, elementShift);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, CustomBuffer<?> buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset       the index in bytes of the first byte to replace at and after.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    public void replaceAfter(long offset, Buffer buffer, int elementShift) {
        cache.replaceAfter(offset, buffer, elementShift);
        wrapped.replaceAfter(offset, buffer, elementShift);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, CustomBuffer<?> buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff       the position in bytes to replace everything before.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, Buffer buffer, int elementShift) {
        cache.replaceBefore(cutoff, buffer, elementShift);
        wrapped.replaceBefore(cutoff, buffer, elementShift);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, CustomBuffer<?> buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer       the new chunk of data to replace everything in this buffer with.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    public void replaceAll(Buffer buffer, int elementShift) {
        cache.replaceAll(buffer, elementShift);
        wrapped.replaceAll(buffer, elementShift);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(CustomBuffer<?> buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
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
        cache.remove(offset, chunkLength);
        wrapped.remove(offset, chunkLength);
    }

    /**
     * Removes everything at and after offset from this buffer, resizing as necessary.
     *
     * @param offset the position in bytes of the first byte to remove at and after.
     */
    @Override
    public void removeAfter(long offset) {
        cache.removeAfter(offset);
        wrapped.removeAfter(offset);
    }

    /**
     * Removes everything before cutoff from this buffer, resizing as necessary.
     *
     * @param cutoff the position in bytes to remove everything before.
     */
    @Override
    public void removeBefore(long cutoff) {
        cache.removeBefore(cutoff);
        wrapped.removeBefore(cutoff);
    }

    /**
     * Clears this buffer.
     * <p>
     * Some implementations may simply implement this by setting their official size to 0.
     */
    @Override
    public void clear() {
        cache.clear();
        wrapped.clear();
    }

    /**
     * Gets the current size of this buffer object.
     * <p>
     * This is simply the official size of this buffer, not the size of any underlying buffers this buffer might use.
     *
     * @return the current size of this buffer object.
     */
    @Override
    public long getSize() {
        return cache.getSize();
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
        cache.setSize(size);
        wrapped.setSize(size);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, ByteBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single byte at offset from this buffer.
     *
     * @param offset the position in bytes of the byte to read.
     * @return the byte read.
     */
    @Override
    public byte readByte(long offset) {
        return cache.readByte(offset);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, ShortBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single short at offset from this buffer.
     *
     * @param offset the position in bytes of the short to read.
     * @return the short read.
     */
    @Override
    public short readShort(long offset) {
        return cache.readShort(offset);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, IntBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single int at offset from this buffer.
     *
     * @param offset the position in bytes of the int to read.
     * @return the int read.
     */
    @Override
    public int readInt(long offset) {
        return cache.readInt(offset);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, LongBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single long at offset from this buffer.
     *
     * @param offset the position in bytes of the long to read.
     * @return the long read.
     */
    @Override
    public long readLong(long offset) {
        return cache.readLong(offset);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, FloatBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single float at offset from this buffer.
     *
     * @param offset the position in bytes of the float to read.
     * @return the float read.
     */
    @Override
    public float readFloat(long offset) {
        return cache.readFloat(offset);
    }

    /**
     * Reads a chunk of data starting at offset into the buffer.
     *
     * @param offset the position in bytes to start reading at.
     * @param buffer the buffer to read the chunk of data into.
     */
    @Override
    public void readTo(long offset, DoubleBuffer buffer) {
        cache.readTo(offset, buffer);
    }

    /**
     * Reads a single double at offset from this buffer.
     *
     * @param offset the position in bytes of the double to read.
     * @return the double read.
     */
    @Override
    public double readDouble(long offset) {
        return cache.readDouble(offset);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, ByteBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single byte within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the byte to set.
     * @param value  the new value of the byte being set.
     */
    @Override
    public void set(long offset, byte value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, ShortBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single short within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the short to set.
     * @param value  the new value of the short being set.
     */
    @Override
    public void set(long offset, short value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, IntBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single int within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the int to set.
     * @param value  the new value of the int being set.
     */
    @Override
    public void set(long offset, int value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, LongBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single long within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the long to set.
     * @param value  the new value of the long being set.
     */
    @Override
    public void set(long offset, long value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, FloatBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single float within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the float to set.
     * @param value  the new value of the float being set.
     */
    @Override
    public void set(long offset, float value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Sets a chunk of data within this buffer to the contents of the specified buffer.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes within this buffer to place the start of the new data.
     * @param buffer the chunk of new data to put into this buffer.
     */
    @Override
    public void set(long offset, DoubleBuffer buffer) {
        cache.set(offset, buffer);
        wrapped.set(offset, buffer);
    }

    /**
     * Sets a single double within this buffer.
     * <p>
     * This will increase the size of this buffer if the value being set extends beyond the current end of this buffer.
     *
     * @param offset the position in bytes of the double to set.
     * @param value  the new value of the double being set.
     */
    @Override
    public void set(long offset, double value) {
        cache.set(offset, value);
        wrapped.set(offset, value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(ByteBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single byte to the end of this buffer.
     *
     * @param value the value of the byte appended to the end of this buffer.
     */
    @Override
    public void append(byte value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(ShortBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single short to the end of this buffer.
     *
     * @param value the value of the short appended to the end of this buffer.
     */
    @Override
    public void append(short value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(IntBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single int to the end of this buffer.
     *
     * @param value the value of the int appended to the end of this buffer.
     */
    @Override
    public void append(int value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(LongBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single long to the end of this buffer.
     *
     * @param value the value of the long appended to the end of this buffer.
     */
    @Override
    public void append(long value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(FloatBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single float to the end of this buffer.
     *
     * @param value the value of the float appended to the end of this buffer.
     */
    @Override
    public void append(float value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    public void append(DoubleBuffer buffer) {
        cache.append(buffer);
        wrapped.append(buffer);
    }

    /**
     * Appends a single double to the end of this buffer.
     *
     * @param value the value of the double appended to the end of this buffer.
     */
    @Override
    public void append(double value) {
        cache.append(value);
        wrapped.append(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(ByteBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single byte at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new byte will be located.
     *
     * @param value the value of the byte to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(byte value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(ShortBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single short at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new short will be located.
     *
     * @param value the value of the short to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(short value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(IntBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single int at the beginning of this buffer, moving everything in the buffer to the end of where the new
     * int will be located.
     *
     * @param value the value of the int to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(int value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(LongBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single long at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new long will be located.
     *
     * @param value the value of the long to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(long value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(FloatBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single float at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new float will be located.
     *
     * @param value the value of the float to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(float value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(DoubleBuffer buffer) {
        cache.prepend(buffer);
        wrapped.prepend(buffer);
    }

    /**
     * Inserts a single double at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new double will be located.
     *
     * @param value the value of the double to prepend at the beginning of this buffer.
     */
    @Override
    public void prepend(double value) {
        cache.prepend(value);
        wrapped.prepend(value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, ByteBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single byte into this buffer at offset, moving the data currently after offset to the end of the space
     * where the byte will be inserted.
     *
     * @param offset the position in bytes to insert the byte at.
     * @param value  the value of the byte to insert.
     */
    @Override
    public void insert(long offset, byte value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, ShortBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single short into this buffer at offset, moving the data currently after offset to the end of the space
     * where the short will be inserted.
     *
     * @param offset the position in bytes to insert the short at.
     * @param value  the value of the short to insert.
     */
    @Override
    public void insert(long offset, short value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, IntBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single int into this buffer at offset, moving the data currently after offset to the end of the space
     * where the int will be inserted.
     *
     * @param offset the position in bytes to insert the int at.
     * @param value  the value of the int to insert.
     */
    @Override
    public void insert(long offset, int value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, LongBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single long into this buffer at offset, moving the data currently after offset to the end of the space
     * where the long will be inserted.
     *
     * @param offset the position in bytes to insert the long at.
     * @param value  the value of the long to insert.
     */
    @Override
    public void insert(long offset, long value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, FloatBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single float into this buffer at offset, moving the data currently after offset to the end of the space
     * where the float will be inserted.
     *
     * @param offset the position in bytes to insert the float at.
     * @param value  the value of the float to insert.
     */
    @Override
    public void insert(long offset, float value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    public void insert(long offset, DoubleBuffer buffer) {
        cache.insert(offset, buffer);
        wrapped.insert(offset, buffer);
    }

    /**
     * Inserts a single double into this buffer at offset, moving the data currently after offset to the end of the
     * space where the double will be inserted.
     *
     * @param offset the position in bytes to insert the double at.
     * @param value  the value of the double to insert.
     */
    @Override
    public void insert(long offset, double value) {
        cache.insert(offset, value);
        wrapped.insert(offset, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, ByteBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single byte, moving the data currently after the old chunk of data
     * to the end of the space where the new byte will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new byte to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, byte value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, ShortBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single short, moving the data currently after the old chunk of
     * data to the end of the space where the new short will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new short to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, short value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, IntBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single int, moving the data currently after the old chunk of data
     * to the end of the space where the new int will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new int to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, int value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, LongBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single long, moving the data currently after the old chunk of data
     * to the end of the space where the new long will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new long to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, long value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, FloatBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single float, moving the data currently after the old chunk of
     * data to the end of the space where the new float will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new float to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, float value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces a chunk of data in this buffer with a new chunk of data, moving the data currently after the old chunk
     * of data to the end of the space where the new chunk of data will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param buffer      the new chunk of data to replace the old one.
     */
    @Override
    public void replace(long offset, long chunkLength, DoubleBuffer buffer) {
        cache.replace(offset, chunkLength, buffer);
        wrapped.replace(offset, chunkLength, buffer);
    }

    /**
     * Replaces a chunk of data in this buffer with a single double, moving the data currently after the old chunk of
     * data to the end of the space where the new double will be located.
     *
     * @param offset      the position in bytes of the chunk to be replaced.
     * @param chunkLength the length in bytes of the chunk to be replaced.
     * @param value       the value of the new double to replace the old chunk of data.
     */
    @Override
    public void replace(long offset, long chunkLength, double value) {
        cache.replace(offset, chunkLength, value);
        wrapped.replace(offset, chunkLength, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, ByteBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single byte, resizing this buffer so that it ends at the end of the new
     * byte.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new byte to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, byte value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, ShortBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single short, resizing this buffer so that it ends at the end of the new
     * short.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new short to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, short value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, IntBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single int, resizing this buffer so that it ends at the end of the new
     * int.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new int to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, int value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, LongBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single long, resizing this buffer so that it ends at the end of the new
     * long.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new long to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, long value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, FloatBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single float, resizing this buffer so that it ends at the end of the new
     * float.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new byte to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, float value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceAfter(long offset, DoubleBuffer buffer) {
        cache.replaceAfter(offset, buffer);
        wrapped.replaceAfter(offset, buffer);
    }

    /**
     * Replaces everything after offset with a single double, resizing this buffer so that it ends at the end of the new
     * double.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param value  the new double to replace the old chunk of data.
     */
    @Override
    public void replaceAfter(long offset, double value) {
        cache.replaceAfter(offset, value);
        wrapped.replaceAfter(offset, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, ByteBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single byte, moving the data after the old chunk of data to the end of
     * the space where the new byte will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new byte the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, byte value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, ShortBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single short, moving the data after the old chunk of data to the end of
     * the space where the new short will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new short the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, short value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, IntBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single int, moving the data after the old chunk of data to the end of the
     * space where the new int will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new int the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, int value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, LongBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single long, moving the data after the old chunk of data to the end of
     * the space where the new long will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new long the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, long value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, FloatBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single float, moving the data after the old chunk of data to the end of
     * the space where the new float will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new float the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, float value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    public void replaceBefore(long cutoff, DoubleBuffer buffer) {
        cache.replaceBefore(cutoff, buffer);
        wrapped.replaceBefore(cutoff, buffer);
    }

    /**
     * Replaces everything before cutoff with a single double, moving the data after the old chunk of data to the end of
     * the space where the new double will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param value  the value of the new double the replace the old chunk of data.
     */
    @Override
    public void replaceBefore(long cutoff, double value) {
        cache.replaceBefore(cutoff, value);
        wrapped.replaceBefore(cutoff, value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(ByteBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single byte, resizing this buffer to match.
     *
     * @param value the value of the new byte to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(byte value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(ShortBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single short, resizing this buffer to match.
     *
     * @param value the value of the new short to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(short value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(IntBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single int, resizing this buffer to match.
     *
     * @param value the value of the new int to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(int value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(LongBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single long, resizing this buffer to match.
     *
     * @param value the value of the new long to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(long value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(FloatBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single float, resizing this buffer to match.
     *
     * @param value the value of the new float to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(float value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Replaces everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(DoubleBuffer buffer) {
        cache.replaceAll(buffer);
        wrapped.replaceAll(buffer);
    }

    /**
     * Replaces everything in this buffer with a single double, resizing this buffer to match.
     *
     * @param value the value of the new double to replace everything in this buffer with.
     */
    @Override
    public void replaceAll(double value) {
        cache.replaceAll(value);
        wrapped.replaceAll(value);
    }

    /**
     * Closes this BufferObject and releases its underlying buffers.
     * If the BufferObject is already closed then invoking this
     * method has no effect.
     */
    @Override
    public void close() {
        cache.close();
    }
}
