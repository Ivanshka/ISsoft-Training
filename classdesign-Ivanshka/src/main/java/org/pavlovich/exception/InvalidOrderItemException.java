package org.pavlovich.exception;

public class InvalidOrderItemException extends RuntimeException {
    public InvalidOrderItemException(String message){
        super(message);
    }

    private InvalidOrderItemException() { }
}
