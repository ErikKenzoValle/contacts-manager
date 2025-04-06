package com.erik.contactsmanager.interfaces.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ContactRequest {

    private PropertiesRequest properties;
    private List<AssociationsRequest> associations;

}
