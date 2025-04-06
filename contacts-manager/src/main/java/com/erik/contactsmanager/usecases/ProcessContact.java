package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.dto.request.WebhookRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ProcessContact {

    public void execute(WebhookRequest webhookRequest) {

        if (webhookRequest != null) {
            Map<String, Object> properties = webhookRequest.getProperties();
            String email = (String) properties.get("email");

            log.info("Novo contato foi criado via webhook. Email: {}", email);
        }
    }

}
