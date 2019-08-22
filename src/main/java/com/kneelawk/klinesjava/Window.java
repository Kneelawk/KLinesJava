package com.kneelawk.klinesjava;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Wrapper around a GLFW window.
 */
public class Window {
    private final long windowName;

    /**
     * Creates a window using the hints specified using {@link SystemInterface#windowHints()}.
     *
     * @param width  the width of the window.
     * @param height the height of the window.
     * @param title  the title of the window.
     */
    public Window(int width, int height, String title) {
        windowName = glfwCreateWindow(width, height, title, NULL, NULL);
    }

    /**
     * Shows this window. From the user's point of view, this is the window opening.
     */
    public void show() {
        glfwShowWindow(windowName);
    }

    /**
     * Hides this window. From the user's point of view, this the window closing.
     */
    public void hide() {
        glfwHideWindow(windowName);
    }

    /**
     * Registers a listener for keyboard events on this window.
     *
     * @param callbackI the listener to be called on a keyboard event.
     */
    public void registerKeyCallback(GLFWKeyCallbackI callbackI) {
        glfwSetKeyCallback(windowName, callbackI);
    }

    /**
     * Uses this window for OpenGL calls.
     */
    public void selectContext() {
        glfwMakeContextCurrent(windowName);
    }

    /**
     * Requests that this window begin begin closing deallocation of system resources. This is equivalent to the user
     * pressing the window's close button.
     */
    public void requestClose() {
        glfwSetWindowShouldClose(windowName, true);
    }

    /**
     * Checks if the operating system, window manager, or application has requested that this window be closed.
     *
     * @return true if a request has been made to close this window.
     */
    public boolean isCloseRequested() {
        return glfwWindowShouldClose(windowName);
    }

    /**
     * Destroys this GLFW window. The window is no longer usable after a call to this method.
     */
    public void destroy() {
        glfwDestroyWindow(windowName);
    }
}
