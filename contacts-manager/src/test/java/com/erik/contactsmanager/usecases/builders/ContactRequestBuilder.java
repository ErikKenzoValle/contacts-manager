package com.erik.contactsmanager.usecases.builders;

import com.erik.contactsmanager.interfaces.dto.request.ContactRequest;
import com.erik.contactsmanager.interfaces.dto.request.PropertiesRequest;

public class ContactRequestBuilder {

    public static ContactRequest generateContactRequest() {
        return ContactRequest.builder()
                .properties(PropertiesRequest.builder()
                        .email("email@teste.com")
                        .build())
                .build();
    }

}
