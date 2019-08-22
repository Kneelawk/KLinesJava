package com.kneelawk.klinesjava;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class KLinesJavaMain extends KLinesEngine {
    public static void main(String[] args) {
        KLinesJavaMain main = new KLinesJavaMain();
        main.windowTitle = "KLines Java";
        main.launch();
    }
}
