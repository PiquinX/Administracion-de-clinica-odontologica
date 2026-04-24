package com.clinica.odontologica.exceptions;

public class TurnoConflictException extends RuntimeException {
    public TurnoConflictException(String message) {
        super(message);
    }

    public TurnoConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
