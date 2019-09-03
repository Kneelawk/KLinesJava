package com.kneelawk.klinesjava.graphics;

import com.kneelawk.klinesjava.buffers.databuffer.WritableGLArrayDataBuffer;
import com.kneelawk.klinesjava.buffers.objectbuffer.WritableIndexingObjectBuffer;
import com.kneelawk.klinesjava.buffers.objectbuffer.WritableObjectBuffer;
import com.kneelawk.klinesjava.buffers.objectbuffer.data.WritableMatrix4fBuffer;
import com.kneelawk.klinesjava.buffers.objectbuffer.data.WritableVector3fBuffer;

public class GraphicsEngine {
    WritableGLArrayDataBuffer positions = new WritableGLArrayDataBuffer();
    WritableGLArrayDataBuffer colors = new WritableGLArrayDataBuffer();
    WritableGLArrayDataBuffer transforms = new WritableGLArrayDataBuffer();
    WritableGLArrayDataBuffer transformIndices = new WritableGLArrayDataBuffer();
    WritableGLArrayDataBuffer vertexIndices = new WritableGLArrayDataBuffer();

    WritableObjectBuffer<Vertex> vertices = new WritableIndexingObjectBuffer<>(vertexIndices,
            new WritableVertexBuffer(new WritableVector3fBuffer(positions), new WritableVector3fBuffer(colors),
                    new WritableIndexingObjectBuffer<>(transformIndices, new WritableMatrix4fBuffer(transforms))));
}
