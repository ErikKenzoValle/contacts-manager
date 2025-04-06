package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.controller.exceptions.CreateContactException;
import com.erik.contactsmanager.interfaces.controller.exceptions.HubspotIntegrationException;
import com.erik.contactsmanager.interfaces.controller.exceptions.RateLimitExceededException;
import com.erik.contactsmanager.interfaces.dto.request.ContactRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CreateContact {

    private static final String CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";

    public void execute(String accessToken, ContactRequest contactRequest) throws JsonProcessingException, HubspotIntegrationException {

        try {
            log.info("Iniciando a criação de contato...");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            String requestBody = new ObjectMapper().writeValueAsString(contactRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            log.info("Enviando requisição de criação de contato a API do Hubspot...");
            ResponseEntity<String> response = restTemplate.exchange(
                    CONTACT_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                log.error("Limite excedido de chamadas, tente novamente mais tarde.");
                throw new RateLimitExceededException("Limite excedido de chamadas, tente novamente mais tarde.");
            }

            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("Contato criado com sucesso!");
            } else {
                log.error("Erro na integração com a API do Hubspot ao criar contato.");
                throw new HubspotIntegrationException("Erro na integração com a API do Hubspot ao criar contato: " + response.getBody());
            }
        }
        catch (Exception e) {
            if (!(e instanceof RateLimitExceededException) && !(e instanceof HubspotIntegrationException)) {
                log.error("Ocorreu um erro na criação de contato: " + e);
                throw new CreateContactException("Ocorreu um erro na criação de contato: " + e.getMessage());
            }
            throw e;
        }
    }
}