package com.kneelawk.klinesjava.buffers.objectbuffer.data;

import com.kneelawk.klinesjava.buffers.databuffer.WritableDataBuffer;
import org.joml.Vector3fc;

import java.nio.ByteBuffer;

public class WritableVector3fBuffer extends AbstractWritableDataObjectBuffer<Vector3fc> {
    private static final int ELEMENT_SIZE = 12;

    public WritableVector3fBuffer(WritableDataBuffer buffer) {
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
    protected void writeElement(ByteBuffer buffer, int position, Vector3fc element) {
        element.get(position, buffer);
    }
}
