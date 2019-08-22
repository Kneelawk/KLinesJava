package com.kneelawk.klinesjava;

public class KLinesEngine {
    protected int windowWidth = 1280;
    protected int windowHeight = 720;
    protected String windowTitle = "KLines Engine";
    protected Window window;
    protected Camera camera;

    public final void launch() {
        init();

        showWindow();

        mainLoop();

        destroy();
    }

    private void init() {
        initWindow();
        initContext();
        initCamera();
    }

    private void initWindow() {
        try {
            SystemInterface.init();
        } catch (EngineException e) {
            System.err.println("Unable to initialize the system interface");
            e.printStackTrace();
            System.exit(-1);
        }

        SystemInterface.windowHints();
        window = new Window(windowWidth, windowHeight, windowTitle);
        window.selectContext();
    }

    private void initContext() {
        GraphicsInterface.setupContext();
    }

    private void initCamera() {
        camera = new Camera();
        camera.setAspect(((float) windowHeight) / ((float) windowWidth));
        camera.update();
    }

    private void showWindow() {
        window.show();
    }

    private void mainLoop() {
        while (!window.isCloseRequested()) {
            SystemInterface.pollEvents();
        }
    }

    private void destroy() {
        destroyWindow();
    }

    private void destroyWindow() {
        window.destroy();
        SystemInterface.terminate();
    }
}
