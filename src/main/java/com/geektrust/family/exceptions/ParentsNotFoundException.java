package com.geektrust.family.exceptions;

public class ParentsNotFoundException extends RuntimeException {
    public ParentsNotFoundException(String message) {
        super(message);
    }
}
