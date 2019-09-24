package com.kneelawk.klinesjava.shaders;

import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32C.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40C.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40C.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43C.GL_COMPUTE_SHADER;

/**
 * An enum wrapper around the different types of shader components that OpenGL can use.
 */
public enum ShaderType {
    /**
     * GL_VERTEX_SHADER
     */
    VERTEX(GL_VERTEX_SHADER),
    /**
     * GL_TESS_CONTROL_SHADER
     */
    TESSELLATION_CONTROL(GL_TESS_CONTROL_SHADER),
    /**
     * GL_TESS_EVALUATION_SHADER
     */
    TESSELLATION_EVALUATION(GL_TESS_EVALUATION_SHADER),
    /**
     * GL_GEOMETRY_SHADER
     */
    GEOMETRY(GL_GEOMETRY_SHADER),
    /**
     * GL_FRAGMENT_SHADER
     */
    FRAGMENT(GL_FRAGMENT_SHADER),
    /**
     * GL_COMPUTE_SHADER
     */
    COMPUTE(GL_COMPUTE_SHADER);

    private final int typeId;

    ShaderType(int typeId) {
        this.typeId = typeId;
    }

    /**
     * Gets the OpenGL constant value of this type.
     *
     * @return the OpenGL constant value of this type.
     */
    public int getTypeId() {
        return typeId;
    }
}
