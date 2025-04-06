package com.erik.contactsmanager.interfaces.controller.exceptions.handler;

public class InvalidAuthenticationUrlException extends RuntimeException {
    public InvalidAuthenticationUrlException(String message) {
        super(message);
    }
}