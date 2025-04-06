package com.erik.contactsmanager.interfaces.controller.exceptions;

public class CreateContactException extends RuntimeException {
    public CreateContactException(String errorMessage) {
        super(errorMessage);
    }
}
