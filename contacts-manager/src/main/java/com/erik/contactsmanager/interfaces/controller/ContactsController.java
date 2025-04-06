package com.erik.contactsmanager.interfaces.controller;

import com.erik.contactsmanager.interfaces.controller.exceptions.HubspotIntegrationException;
import com.erik.contactsmanager.interfaces.dto.request.ContactRequest;
import com.erik.contactsmanager.usecases.CreateContact;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/contacts")
public class ContactsController {

    private final CreateContact createContact;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createContact(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody ContactRequest contactRequest)
            throws JsonProcessingException, HubspotIntegrationException {
        createContact.execute(accessToken, contactRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}