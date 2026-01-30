package com.hostelbooking.app.exception;

public class HostelAlreadyExistsException extends RuntimeException {
    public HostelAlreadyExistsException(String message){
        super(message);
    }
}
