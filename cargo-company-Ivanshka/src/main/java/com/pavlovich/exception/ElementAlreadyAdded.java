package com.pavlovich.exception;

public class ElementAlreadyAdded extends RuntimeException {
    public ElementAlreadyAdded(String message){
        super(message);
    }
}
