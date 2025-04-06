package com.erik.contactsmanager.interfaces.controller;

import com.erik.contactsmanager.interfaces.dto.request.WebhookRequest;
import com.erik.contactsmanager.usecases.ProcessContact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/webhooks")
public class WebhookController {

    private final ProcessContact processContact;

    @PostMapping("/contact-creation")
    public ResponseEntity<String> handleContactCreationWebhook(@RequestBody WebhookRequest webhookRequest) {
        processContact.execute(webhookRequest);

        return ResponseEntity.ok("Evento de criação de contato processado com sucesso.");
    }
}