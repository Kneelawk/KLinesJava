module com.kneelawk.klinesjava {
    exports com.kneelawk.klinesjava;
    exports com.kneelawk.klinesjava.buffers;
    exports com.kneelawk.klinesjava.shaders;

    requires com.google.common;

    requires org.joml;

    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
}