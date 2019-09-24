package com.kneelawk.klinesjava;

import static org.lwjgl.glfw.GLFW.*;

public class SystemInterface {
    /**
     * Initializes the GLFW system interface and context.
     *
     * @throws EngineException if initializing GLFW failed
     */
    public static void init() throws EngineException {
        if (!glfwInit()) {
            throw new EngineException("Failed to initialize GLFW");
        }
    }

    /**
     * Sets the this application's default window hints.
     * This is designed to be called right before creating a window, because these attributes are static.
     */
    public static void windowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    }

    /**
     * Refreshes GLFW's event queue.
     */
    public static void pollEvents() {
        glfwPollEvents();
    }

    /**
     * Shuts down the GLFW system interface and context.
     * Using these again requires you re-initialize GLFW with a call to {@link #init()}.
     */
    public static void terminate() {
        glfwTerminate();
    }
}
