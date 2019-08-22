package com.kneelawk.klinesjava.shaders;

import com.kneelawk.klinesjava.EngineException;

/**
 * An exception thrown if there was an error while compiling a shader.
 */
public class ShaderCompileException extends EngineException {
    public ShaderCompileException() {
    }

    public ShaderCompileException(String message) {
        super(message);
    }

    public ShaderCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShaderCompileException(Throwable cause) {
        super(cause);
    }
}
