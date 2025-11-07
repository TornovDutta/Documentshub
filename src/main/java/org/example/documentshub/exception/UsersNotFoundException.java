package org.example.documentshub.exception;

public class UsersNotFoundException extends Exception{
    public UsersNotFoundException(){
        super("user not found");
    }
    public UsersNotFoundException(String message){
        super(message);
    }
    public UsersNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
