package com.kneelawk.klinesjava.buffers;

import org.lwjgl.system.CustomBuffer;

import java.nio.Buffer;

import static org.lwjgl.system.MemoryUtil.memAddress;

public interface WritableNativeBufferObject extends WritableBufferObject {

    /**
     * Sets a chunk of data within this buffer to the contents of the buffer specified by length and address.
     * <p>
     * This will increase the size of this buffer if the chunk of data being set expends beyond the current end of this buffer.
     *
     * @param offset  the position in bytes within this buffer to place the start of the new data.
     * @param length  the length in bytes of the chunk of data.
     * @param address the address of the buffer containing the new chunk of data.
     */
    void setNative(long offset, long length, long address);

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
    default void set(long offset, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        setNative(offset, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
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
    default void set(long offset, CustomBuffer<?> buffer) {
        setNative(offset, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param length  the length in bytes of the chunk of data to append.
     * @param address the address of the buffer containing the chunk of data to append.
     */
    void appendNative(long length, long address);

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer       the chunk of data to append to the end of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    default void append(Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        appendNative(((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Appends a chunk of data to the end of this buffer.
     *
     * @param buffer the chunk of data to append to the end of this buffer.
     */
    @Override
    default void append(CustomBuffer<?> buffer) {
        appendNative(((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param length  the length in bytes of the chunk of data to prepend.
     * @param address the address of the buffer containing the chunk of data to prepend.
     */
    void prependNative(long length, long address);

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer       the chunk of data to prepend at the beginning of this buffer.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    default void prepend(Buffer buffer, int elementShift) {
        prependNative(((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Inserts a chunk of data at the beginning of this buffer, moving everything in the buffer to the end of where the
     * new chunk of data will be located.
     *
     * @param buffer the chunk of data to prepend at the beginning of this buffer.
     */
    @Override
    default void prepend(CustomBuffer<?> buffer) {
        prependNative(((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be located.
     *
     * @param offset  the position in bytes to insert the chunk of data at.
     * @param length  the length in bytes of the chunk of data to insert.
     * @param address the address of the buffer containing the chunk of data to insert.
     */
    void insertNative(long offset, long length, long address);

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be located.
     *
     * @param offset       the position in bytes to insert the chunk of data at.
     * @param buffer       the chunk of data to insert.
     * @param elementShift the power of two that is the size of each element in the chunk of data.
     */
    @Override
    default void insert(long offset, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        insertNative(offset, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Inserts a chunk of data into this buffer at offset, moving the data currently after offset to the end of the
     * space where the new chunk of data will be inserted.
     *
     * @param offset the position in bytes to insert the chunk of data at.
     * @param buffer the chunk of data to insert.
     */
    @Override
    default void insert(long offset, CustomBuffer<?> buffer) {
        insertNative(offset, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
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
    void replaceNative(long offset, long chunkLength, long length, long address);

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
    default void replace(long offset, long chunkLength, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        replaceNative(offset, chunkLength, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
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
    default void replace(long offset, long chunkLength, CustomBuffer<?> buffer) {
        replaceNative(offset, chunkLength, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Replace everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset  the index in bytes of the first byte to replace at and after.
     * @param length  the length in bytes of the chunk of data to replace the the old chunk.
     * @param address the address of the buffer containing the chunk of data to replace the old chunk.
     */
    void replaceAfterNative(long offset, long length, long address);

    /**
     * Replace everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset       the index in bytes of the first byte to replace at and after.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    default void replaceAfter(long offset, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        replaceAfterNative(offset, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Replace everything after offset with a new chunk of data, resizing this buffer so that it ends at the end of the
     * new chunk of data.
     *
     * @param offset the index in bytes of the first byte to replace at and after.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    default void replaceAfter(long offset, CustomBuffer<?> buffer) {
        replaceAfterNative(offset, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Replace everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff  the position in bytes to replace everything before.
     * @param length  the length in bytes of the new chunk of data to replace the old chunk.
     * @param address the address of the buffer containing the new chunk of data to replace the old chunk.
     */
    void replaceBeforeNative(long cutoff, long length, long address);

    /**
     * Replace everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff       the position in bytes to replace everything before.
     * @param buffer       the new chunk of data to replace the old one.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    default void replaceBefore(long cutoff, Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        replaceBeforeNative(cutoff, ((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Replace everything before cutoff with a new chunk of data, moving the data after the old chunk of data to the end
     * of the space where the new chunk of data will be located.
     *
     * @param cutoff the position in bytes to replace everything before.
     * @param buffer the new chunk of data to replace the old one.
     */
    @Override
    default void replaceBefore(long cutoff, CustomBuffer<?> buffer) {
        replaceBeforeNative(cutoff, ((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }

    /**
     * Replace everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param length  the length of the new chunk of data to replace everything in this buffer with.
     * @param address the address of the buffer containing the new chunk of data to replace everything in this buffer with.
     */
    void replaceAllNative(long length, long address);

    /**
     * Replace everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer       the new chunk of data to replace everything in this buffer with.
     * @param elementShift the power of two that is the size of each element in the new chunk of data.
     */
    @Override
    default void replaceAll(Buffer buffer, int elementShift) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("This buffer only supports direct java nio buffers");
        }

        replaceAllNative(((long) buffer.remaining()) << ((long) elementShift), memAddress(buffer));
    }

    /**
     * Replace everything in this buffer with a new chunk of data, resizing this buffer to match that of the new chunk
     * of data.
     *
     * @param buffer the new chunk of data to replace everything in this buffer with.
     */
    @Override
    default void replaceAll(CustomBuffer<?> buffer) {
        replaceAllNative(((long) buffer.remaining()) * ((long) buffer.sizeof()), memAddress(buffer));
    }
}
