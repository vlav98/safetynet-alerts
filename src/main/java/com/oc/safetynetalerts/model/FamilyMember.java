package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyMember {
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
}