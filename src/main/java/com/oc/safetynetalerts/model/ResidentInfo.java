package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResidentInfo {
    private String lastName;
    private String phone;
    private Integer age;
    private List<String> medications;
    private List<String> allergies;
}
