package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    public static List<MedicalRecord> medicalRecords;

    public MedicalRecord findMedicalRecordByFullName(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
    }

    public boolean save(MedicalRecord medicalRecord) {
        if (medicalRecord != null && medicalRecords.stream()
                .filter(filteredMedicalRecord -> filteredMedicalRecord.getFirstName().equals(medicalRecord.getFirstName())
                && filteredMedicalRecord.getLastName().equals(medicalRecord.getLastName())).findFirst().isEmpty()) {
            medicalRecords.add(medicalRecord);
            return true;
        }
        return false;
    }

    public boolean updateByFullName(MedicalRecord medicalRecord) {
        int searchedMedicalRecordIndex = medicalRecords.indexOf(findMedicalRecordByFullName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        if (searchedMedicalRecordIndex != -1) {
            medicalRecords.set(searchedMedicalRecordIndex, medicalRecord);
            return true;
        }
        return false;
    }

    public boolean deleteByFullName(MedicalRecord medicalRecord) {
        int searchedPersonIndex = medicalRecords.indexOf(findMedicalRecordByFullName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        if (searchedPersonIndex != -1) {
            medicalRecords.remove(searchedPersonIndex);
            return true;
        }
        return false;
    }

}
