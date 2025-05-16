package com.monitoring_mikrohidro_read.exceptions;

public class DatabaseUpdateException extends RuntimeException {
    public DatabaseUpdateException(String message) {
        super(message);
    }

    public DatabaseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DatabaseUpdateException(Throwable cause) {
        super(cause);
    }
}
