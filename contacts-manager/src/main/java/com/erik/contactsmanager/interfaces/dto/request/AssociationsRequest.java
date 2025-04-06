package com.erik.contactsmanager.interfaces.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AssociationsRequest {
    private To to;
    private List<Type> types;

    @Data
    public static class To {
        private Integer id;
    }

    @Data
    public static class Type {
        private String associationCategory;
        private Integer associationTypeId;
    }
}
