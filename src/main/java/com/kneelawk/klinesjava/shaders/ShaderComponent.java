package com.kneelawk.klinesjava.shaders;

import java.util.Objects;

import static org.lwjgl.opengl.GL20C.glDeleteShader;

/**
 * Wraps a component of an OpenGL shader.
 */
public class ShaderComponent {
    private final int id;

    /**
     * Creates this component with the given id.
     *
     * @param id the id of this component.
     */
    public ShaderComponent(int id) {
        this.id = id;
    }

    /**
     * Gets the id of this component.
     *
     * @return the id of this component.
     */
    public int getId() {
        return id;
    }

    /**
     * Destroys this shader component. This can be done as soon as it has been linked into a ShaderProgram.
     */
    public void destroy() {
        glDeleteShader(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShaderComponent that = (ShaderComponent) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
