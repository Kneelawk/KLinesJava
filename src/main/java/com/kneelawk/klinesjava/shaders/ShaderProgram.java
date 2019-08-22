package com.kneelawk.klinesjava.shaders;

import java.util.Objects;

import static org.lwjgl.opengl.GL20C.glDeleteProgram;
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;

/**
 * Wraps a compiled and linked shader program.
 */
public class ShaderProgram {
    private final int id;

    /**
     * Creates a wrapper for the program with the given id.
     *
     * @param id the id of the program to create this wrapper for.
     */
    public ShaderProgram(int id) {
        this.id = id;
    }

    /**
     * Gets the id of the program.
     * <p>
     * This is sometimes also called the program's name in OpenGL functions.
     *
     * @return the id of this program.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the handle of a uniform within this program.
     *
     * @param name the textual name of the uniform.
     * @return the handle of the uniform.
     */
    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    /**
     * Deletes this program.
     * <p>
     * This should be done when the program is no longer being used. (e.g. when the application is shutting down)
     */
    public void destroy() {
        glDeleteProgram(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShaderProgram that = (ShaderProgram) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
