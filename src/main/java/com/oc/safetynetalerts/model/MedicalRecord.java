package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecord {
    private String lastName;
    private String firstName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

}
