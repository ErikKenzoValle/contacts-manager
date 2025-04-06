package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.controller.exceptions.HubspotIntegrationException;
import com.erik.contactsmanager.interfaces.controller.exceptions.ProcessCallbackException;
import com.erik.contactsmanager.interfaces.controller.exceptions.RateLimitExceededException;
import com.erik.contactsmanager.interfaces.dto.response.HubspotTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ProcessCallback {

    private static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    public String execute(String code) {
        try {
            log.info("Iniciando o processamento do callback...");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("redirect_uri", redirectUri);
            body.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            log.info("Enviando requisição de processamento do callback a API do Hubspot...");
            ResponseEntity<HubspotTokenResponse> response = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    request,
                    HubspotTokenResponse.class
            );

            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                log.error("Limite excedido de chamadas, tente novamente mais tarde.");
                throw new RateLimitExceededException("Limite excedido de chamadas, tente novamente mais tarde.");
            }

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Callback processado com sucesso!");
                return response.getBody().getAccessToken();
            } else {
                log.error("Erro na integração com a API do Hubspot ao processar o callback!");
                throw new HubspotIntegrationException("Erro na integração com a API do Hubspot ao processar o callback: " + response.getBody());
            }
        }
        catch (Exception e) {
            log.error("Ocorreu um erro no processamento do callback: " + e);
            throw new ProcessCallbackException("Ocorreu um erro no processamento do callback: " + e.getMessage());
        }
    }
}