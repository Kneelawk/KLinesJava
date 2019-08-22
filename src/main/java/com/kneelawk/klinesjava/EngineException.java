package com.kneelawk.klinesjava;

/**
 * Thrown when an error occurs in the engine.
 */
public class EngineException extends Exception {
    public EngineException() {
    }

    public EngineException(String message) {
        super(message);
    }

    public EngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineException(Throwable cause) {
        super(cause);
    }
}
