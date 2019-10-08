module com.kneelawk.klinesjava {
    exports com.kneelawk.klinesjava;
    exports com.kneelawk.klinesjava.buffers;
    exports com.kneelawk.klinesjava.buffers.databuffer;
    exports com.kneelawk.klinesjava.buffers.objectbuffer;
    exports com.kneelawk.klinesjava.buffers.objectbuffer.data;
    exports com.kneelawk.klinesjava.graphics;
    exports com.kneelawk.klinesjava.shaders;
    exports com.kneelawk.klinesjava.utils;

    requires com.google.common;

    requires org.joml;

    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
}