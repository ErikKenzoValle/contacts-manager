package com.erik.contactsmanager.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PropertiesRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String phone;
    @NotBlank
    private String company;
    @NotBlank
    private String website;
    @NotBlank
    private String lifecyclestage;

}
