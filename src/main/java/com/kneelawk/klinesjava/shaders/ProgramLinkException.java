package com.kneelawk.klinesjava.shaders;

import com.kneelawk.klinesjava.EngineException;

/**
 * An exception thrown if there was an error while linking a shader program.
 */
public class ProgramLinkException extends EngineException {
    public ProgramLinkException() {
    }

    public ProgramLinkException(String message) {
        super(message);
    }

    public ProgramLinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProgramLinkException(Throwable cause) {
        super(cause);
    }
}
