package com.kneelawk.klinesjava.buffers.objectbuffer.data;

import com.kneelawk.klinesjava.buffers.databuffer.WritableDataBuffer;
import org.joml.Matrix4fc;

import java.nio.ByteBuffer;

public class WritableMatrix4fBuffer extends AbstractWritableDataObjectBuffer<Matrix4fc> {
    private static final int BYTES_PER_FLOAT = 4;
    private static final int FLOATS_PER_ELEMENT = 16;
    private static final int ELEMENT_SIZE = BYTES_PER_FLOAT * FLOATS_PER_ELEMENT;

    public WritableMatrix4fBuffer(WritableDataBuffer buffer) {
        super(buffer, ELEMENT_SIZE);
    }

    /**
     * Used to write a single element to a buffer at a specific position.
     * <p>
     * The buffer's position and limit should be the same after this method as they were before.
     *
     * @param buffer   the buffer to write the element to.
     * @param position the position within the buffer to write the element at.
     * @param element  the element to write.
     */
    @Override
    protected void writeElement(ByteBuffer buffer, int position, Matrix4fc element) {
        element.get(position, buffer);
    }
}
