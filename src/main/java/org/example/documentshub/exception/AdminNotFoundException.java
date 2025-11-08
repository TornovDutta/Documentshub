package org.example.documentshub.exception;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException(){
        super("admin not found");
    }
    public AdminNotFoundException(String message){
        super(message);
    }
    public AdminNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
