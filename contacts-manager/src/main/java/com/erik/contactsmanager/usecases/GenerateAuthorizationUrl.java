package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.controller.exceptions.handler.InvalidAuthenticationUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class GenerateAuthorizationUrl {

    private static final String BASE_URL = "https://app.hubspot.com/oauth/authorize";

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    @Value("${hubspot.scopes}")
    private String scopes;

    public String execute() {
        log.info("Iniciando a geração da URL de autenticação...");

        if(!clientId.isBlank() && !redirectUri.isBlank() && !scopes.isBlank()) {
            String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
            String encodedScopes = scopes.replace(" ", "%20");
            String authUrl = BASE_URL + "?client_id=" + clientId + "&redirect_uri=" + encodedRedirectUri
                    + "&scope=" + encodedScopes;

            log.info("URL de autenticação gerada com sucesso!");
            return authUrl;
        }
        else {
            log.error("Não é possível gerar a URL de autenticação sem todos os campos internos necessários!");
            throw new InvalidAuthenticationUrlException("Não é possível gerar a URL de autenticação sem todos os campos internos necessários!");
        }
    }

}