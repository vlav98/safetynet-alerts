package com.oc.safetynetalerts.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RawData implements Serializable {
    private List<Person> persons;
    private List<FireStation> fireStations;
    private List<MedicalRecord> medicalRecords;

}
