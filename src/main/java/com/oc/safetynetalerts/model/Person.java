package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String email;

}
