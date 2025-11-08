package org.example.documentshub.exception;

public class VersionNotFoundException extends RuntimeException {
    public VersionNotFoundException(String message) {
        super(message);
    }
    public VersionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public VersionNotFoundException() {
        super("version number not found");
    }
}
