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

    public MedicalRecord save(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord updateByFullName(MedicalRecord medicalRecord) {
        int searchedMedicalRecordIndex = medicalRecords.indexOf(findMedicalRecordByFullName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        if (searchedMedicalRecordIndex != -1) {
            medicalRecords.set(searchedMedicalRecordIndex, medicalRecord);
        }
        return medicalRecords.get(searchedMedicalRecordIndex);

    }

    public void deleteByFullName(MedicalRecord medicalRecord) {
        int searchedPersonIndex = medicalRecords.indexOf(findMedicalRecordByFullName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        if (searchedPersonIndex != -1) {
            medicalRecords.remove(searchedPersonIndex);
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }
}
