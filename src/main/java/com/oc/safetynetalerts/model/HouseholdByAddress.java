package com.oc.safetynetalerts.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HouseholdByAddress {
    private String address;
    private List<ResidentInfo> resident;
}
