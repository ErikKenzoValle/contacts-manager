package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.controller.exceptions.handler.InvalidAuthenticationUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateAuthorizationUrlTest {

    @InjectMocks
    private GenerateAuthorizationUrl generateAuthorizationUrl;

    @Test
    @DisplayName("Should generate valid authorization url")
    void executeShouldGenerateValidAuthorizationUrl() {

        String clientId = "test-client-id";
        String redirectUri = "https://localhost:8080/auth/callback";
        String scopes = "contact";

        ReflectionTestUtils.setField(generateAuthorizationUrl, "clientId", clientId);
        ReflectionTestUtils.setField(generateAuthorizationUrl, "redirectUri", redirectUri);
        ReflectionTestUtils.setField(generateAuthorizationUrl, "scopes", scopes);

        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String encodedScopes = scopes.replace(" ", "%20");
        String expectedAuthUrl = "https://app.hubspot.com/oauth/authorize?client_id=" + clientId +
                                     "&redirect_uri=" + encodedRedirectUri +
                                     "&scope=" + encodedScopes;

        String actualAuthUrl = generateAuthorizationUrl.execute();

        assertEquals(expectedAuthUrl, actualAuthUrl);
    }

    @Test
    @DisplayName("Should throw exception when client id is blank")
    void executeShouldThrowExceptionIfClientIdIsBlank() {

        ReflectionTestUtils.setField(generateAuthorizationUrl, "clientId", "");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "redirectUri", "some-uri");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "scopes", "some-scopes");

        assertThrows(InvalidAuthenticationUrlException.class, () -> generateAuthorizationUrl.execute());
    }

    @Test
    @DisplayName("Should throw exception when redirect url is blank")
    void executeShouldThrowExceptionIfRedirectUriIsBlank() {

        ReflectionTestUtils.setField(generateAuthorizationUrl, "clientId", "some-id");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "redirectUri", "");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "scopes", "some-scopes");

        assertThrows(InvalidAuthenticationUrlException.class, () -> generateAuthorizationUrl.execute());
    }

    @Test
    @DisplayName("Should throw exception when redirect url is blank")
    void executeShouldThrowExceptionIfScopesIsBlank() {

        ReflectionTestUtils.setField(generateAuthorizationUrl, "clientId", "some-id");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "redirectUri", "some-uri");
        ReflectionTestUtils.setField(generateAuthorizationUrl, "scopes", "");

        assertThrows(InvalidAuthenticationUrlException.class, () -> generateAuthorizationUrl.execute());
    }

    @Test
    @DisplayName("Should generate authorization url and handle spaces in scopes")
    void executeShouldHandleSpacesInScopes() {

        String clientId = "test-client-id";
        String redirectUri = "https://localhost:8080/auth/callback";
        String scopes = "contacts test email";

        ReflectionTestUtils.setField(generateAuthorizationUrl, "clientId", clientId);
        ReflectionTestUtils.setField(generateAuthorizationUrl, "redirectUri", redirectUri);
        ReflectionTestUtils.setField(generateAuthorizationUrl, "scopes", scopes);

        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String encodedScopes = "contacts%20test%20email";
        String expectedAuthUrl = "https://app.hubspot.com/oauth/authorize?client_id=" + clientId +
                "&redirect_uri=" + encodedRedirectUri +
                "&scope=" + encodedScopes;

        String actualAuthUrl = generateAuthorizationUrl.execute();

        assertEquals(expectedAuthUrl, actualAuthUrl);
    }
}