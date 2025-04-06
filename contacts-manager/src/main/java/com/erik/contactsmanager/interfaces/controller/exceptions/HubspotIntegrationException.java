package com.erik.contactsmanager.interfaces.controller.exceptions;

public class HubspotIntegrationException extends Exception {
    public HubspotIntegrationException(String externalErrorMessage) {
        super(externalErrorMessage);
    }
}
