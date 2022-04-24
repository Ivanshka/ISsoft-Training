package org.pavlovich.exception;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message){
        super(message);
    }

    private InvalidOrderException() { }
}
