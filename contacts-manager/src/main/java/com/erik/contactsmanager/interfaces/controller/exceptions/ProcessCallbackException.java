package com.erik.contactsmanager.interfaces.controller.exceptions;

public class ProcessCallbackException extends RuntimeException {
    public ProcessCallbackException(String errorMessage) {
        super(errorMessage);
    }
}

