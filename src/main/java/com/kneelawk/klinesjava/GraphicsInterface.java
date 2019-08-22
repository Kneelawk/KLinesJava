package com.kneelawk.klinesjava;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11C.*;


public class GraphicsInterface {
    public static void setupContext() {
        GL.createCapabilities(true);

        String renderer = glGetString(GL_RENDERER);
        String version = glGetString(GL_VERSION);
        String vendor = glGetString(GL_VENDOR);

        System.out.println("GL_RENDERER: " + renderer);
        System.out.println("GL_VERSION: " + version);
        System.out.println("GL_VENDOR: " + vendor);
    }
}
