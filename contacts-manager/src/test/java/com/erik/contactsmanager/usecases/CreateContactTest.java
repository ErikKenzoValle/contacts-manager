package com.erik.contactsmanager.usecases;

import com.erik.contactsmanager.interfaces.controller.exceptions.HubspotIntegrationException;
import com.erik.contactsmanager.interfaces.controller.exceptions.RateLimitExceededException;
import com.erik.contactsmanager.interfaces.dto.request.ContactRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static com.erik.contactsmanager.usecases.builders.ContactRequestBuilder.generateContactRequest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CreateContactTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CreateContact createContact;

    //@Test
    void testExecuteSuccess() throws JsonProcessingException, HubspotIntegrationException {

        String accessToken = "valid-access-token";
        ContactRequest contactRequest = generateContactRequest();

        String jsonResponse = "{\"id\": \"123\"}";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);

        //FIXME mock não funcionando
        when(restTemplate.exchange(
                any(), any(), any(), eq(String.class)
        )).thenReturn(responseEntity);

        createContact.execute(accessToken, contactRequest);

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), (HttpEntity<?>) any(HttpEntity.class), eq(String.class));
    }

    //@Test
    void testExecuteRateLimitExceededException() {

        String accessToken = "valid-access-token";
        ContactRequest contactRequest = generateContactRequest();

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);

        //FIXME mock não funcionando
        when(restTemplate.exchange(
                any(), any(), any(), eq(String.class)
        )).thenReturn(responseEntity);

        assertThrows(RateLimitExceededException.class, () -> {
            createContact.execute(accessToken, contactRequest);
        });
    }

    //@Test
    void testExecuteHubspotIntegrationErrorException() {

        String accessToken = "valid-access-token";
        ContactRequest contactRequest = generateContactRequest();

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Error creating contact", HttpStatus.INTERNAL_SERVER_ERROR);

        //FIXME mock não funcionando
        when(restTemplate.exchange(
                any(), any(), any(), eq(String.class)
        )).thenReturn(responseEntity);

        assertThrows(HubspotIntegrationException.class, () -> {
            createContact.execute(accessToken, contactRequest);
        });
    }

    //@Test
    void testExecuteGeneralErrorException() {

        String accessToken = "valid-access-token";
        ContactRequest contactRequest = generateContactRequest();

        //FIXME mock não funcionando
        when(restTemplate.exchange(
                any(), any(), any(), eq(String.class)
        )).thenThrow(new RuntimeException("General Error"));

        assertThrows(HubspotIntegrationException.class, () -> {
            createContact.execute(accessToken, contactRequest);
        });
    }
}
