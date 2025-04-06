package com.erik.contactsmanager.interfaces.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class WebhookRequest {

    private String event;
    private Map<String, Object> properties;

}
