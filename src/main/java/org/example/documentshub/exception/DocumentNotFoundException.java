package org.example.documentshub.exception;

public class DocumentNotFoundException extends Exception{
    public DocumentNotFoundException(){
        super("documents not found");
    }
    public DocumentNotFoundException(String message){
        super(message);
    }
    public DocumentNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
