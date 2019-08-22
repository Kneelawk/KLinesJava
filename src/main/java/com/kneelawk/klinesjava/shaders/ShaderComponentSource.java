package com.kneelawk.klinesjava.shaders;

import static org.lwjgl.opengl.GL20C.*;

/**
 * Supplies the arguments for compiling a shader component from GLSL source code.
 */
public class ShaderComponentSource {
    private String source;
    private String name;
    private ShaderType type;

    /**
     * Creates this component source with default values.
     */
    public ShaderComponentSource() {
        source = "";
        name = "unknown";
        type = ShaderType.VERTEX;
    }

    /**
     * Creates this component source with the given, source code, name, and shader type.
     *
     * @param source the GLSL source code to be compiled.
     * @param name   the name of this shader source. (To be used in logs and error messages.)
     * @param type   the type of this shader component.
     */
    public ShaderComponentSource(String source, String name, ShaderType type) {
        this.source = source;
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the GLSL source code string of this shader component.
     *
     * @return the GLSL source code string of this shader component.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the GLSL source code string of this shader component.
     *
     * @param source the GLSL source code string of this shader component.
     * @return this
     */
    public ShaderComponentSource setSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * Gets the name of this shader component.
     * <p>
     * The name of a shader component is only used in logs and error messages.
     *
     * @return the name of this shader component.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this shader component.
     * <p>
     * The name of a shader component is only used in logs and error messages.
     *
     * @param name the name of this shader component.
     * @return this
     */
    public ShaderComponentSource setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the type of this shader component.
     *
     * @return the type of this shader component.
     * @see ShaderType
     */
    public ShaderType getType() {
        return type;
    }

    /**
     * Sets the type of this shader component.
     *
     * @param type te type of this shader component.
     * @return this
     * @see ShaderType
     */
    public ShaderComponentSource setType(ShaderType type) {
        this.type = type;
        return this;
    }

    /**
     * Compiles this ShaderComponentSource into a ShaderComponent to be used in a ShaderProgram.
     *
     * @return the compiled ShaderComponent.
     * @throws ShaderCompileException if there was an error while compiling the shader.
     * @see ShaderComponent
     * @see UnlinkedShaderProgram
     * @see ShaderProgram
     */
    public ShaderComponent compile() throws ShaderCompileException {
        return compileSource(source, name, type);
    }

    /**
     * Compiles a ShaderComponent from GLSL source with a given type.
     *
     * @param source the GLSL source code of the shader to be compiled.
     * @param name   the name of the shader to be compiled. (Only used in logs and error messages)
     * @param type   the type of the shader to be compiled.
     * @return the compiled ShaderComponent.
     * @throws ShaderCompileException if there was an error while compiling the shader.
     * @see ShaderComponent
     * @see ShaderType
     * @see UnlinkedShaderProgram
     * @see ShaderProgram
     */
    public static ShaderComponent compileSource(String source, String name, ShaderType type)
            throws ShaderCompileException {
        int id = glCreateShader(type.getTypeId());

        glShaderSource(id, source);

        glCompileShader(id);

        String log = glGetShaderInfoLog(id);

        if (!log.isEmpty()) {
            System.err.println(log);
        }

        int status = glGetShaderi(id, GL_COMPILE_STATUS);

        if (status != GL_TRUE) {
            glDeleteShader(id);
            throw new ShaderCompileException(
                    "Unable to compile shader: " + name + ", log: " + log + ", status: " + status);
        }

        return new ShaderComponent(id);
    }
}
