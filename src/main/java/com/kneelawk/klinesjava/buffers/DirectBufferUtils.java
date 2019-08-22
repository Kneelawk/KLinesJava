package com.kneelawk.klinesjava.buffers;

import java.nio.*;

import static org.lwjgl.system.MemoryUtil.*;

public class DirectBufferUtils {
    /**
     * Creates a new direct buffer using {@link org.lwjgl.system.MemoryUtil#memAlloc(int)} or similar and copies the
     * data from heap buffer to the newly created buffer.
     *
     * @param heapBuffer the buffer on the heap to have its data copied.
     * @return the newly created direct buffer.
     */
    public static Buffer makeDirect(Buffer heapBuffer) {
        Buffer heapDuplicate = heapBuffer.duplicate();
        Buffer directBuffer;
        if (heapDuplicate instanceof ByteBuffer) {
            directBuffer = memAlloc(heapDuplicate.remaining());
            ((ByteBuffer) directBuffer).put((ByteBuffer) heapDuplicate);
        } else if (heapDuplicate instanceof ShortBuffer) {
            directBuffer = memAllocShort(heapDuplicate.remaining());
            ((ShortBuffer) directBuffer).put((ShortBuffer) heapDuplicate);
        } else if (heapDuplicate instanceof IntBuffer) {
            directBuffer = memAllocInt(heapDuplicate.remaining());
            ((IntBuffer) directBuffer).put((IntBuffer) heapDuplicate);
        } else if (heapDuplicate instanceof LongBuffer) {
            directBuffer = memAllocLong(heapDuplicate.remaining());
            ((LongBuffer) directBuffer).put((LongBuffer) heapDuplicate);
        } else if (heapDuplicate instanceof FloatBuffer) {
            directBuffer = memAllocFloat(heapDuplicate.remaining());
            ((FloatBuffer) directBuffer).put((FloatBuffer) heapDuplicate);
        } else if (heapDuplicate instanceof DoubleBuffer) {
            directBuffer = memAllocDouble(heapDuplicate.remaining());
            ((DoubleBuffer) directBuffer).put((DoubleBuffer) heapDuplicate);
        } else {
            throw new IllegalArgumentException(
                    "Direct buffer allocation is only supported for byte, short, int, long, float, and double type buffers");
        }

        return directBuffer.flip();
    }
}
