package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChildAlertInfo {
    private String lastName;
    private String firstName;
    private Integer age;
    private List<?> familyMembers;

}
