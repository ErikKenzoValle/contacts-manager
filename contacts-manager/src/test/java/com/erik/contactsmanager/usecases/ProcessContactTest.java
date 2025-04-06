package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.dto.request.WebhookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ProcessContactTest {

    @InjectMocks
    private ProcessContact processContact;

    @Test
    @DisplayName("Should process contact when webhook request is not null")
    void executeShouldProcessContactWhenWebhookRequestIsNotNull() {

        WebhookRequest webhookRequest = new WebhookRequest();
        Map<String, Object> properties = new HashMap<>();
        properties.put("email", "test@example.com");
        webhookRequest.setProperties(properties);

        assertDoesNotThrow(() -> processContact.execute(webhookRequest));
    }

}