package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.MedicalRecord;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class MedicalRepositoryTests {

    MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();


    @Test
    public void findMedicalRecordByFullNameWhenMedicalRecordExistsReturnList() {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        // WHEN
        MedicalRecord medicalRecordResult = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        // THEN
        assertThat(medicalRecordResult).isNotNull();
        assertEquals(firstName, medicalRecordResult.getFirstName());
        assertEquals(lastName, medicalRecordResult.getLastName());
    }


    @Test
    public void canNotFindMedicalRecordByFullNameWhenMedicalRecordDoesNotExistsReturnList() {
        // GIVEN
        String firstName = "John";
        String lastName = "Lee";
        // WHEN
        MedicalRecord medicalRecordResult = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        // THEN
        assertThat(medicalRecordResult).isNull();
    }

    @Test
    public void saveMedicalRecordWithSuccessTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Smith";
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName(firstName);
        newMedicalRecord.setLastName(lastName);
        newMedicalRecord.setBirthdate("01/01/2010");
        newMedicalRecord.setAllergies(new ArrayList<>());
        newMedicalRecord.setMedications(new ArrayList<>());
        // WHEN
        // THEN
        assertThat(medicalRecordRepository.save(newMedicalRecord)).isTrue();
        MedicalRecord savedMedicalRecord = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        assertThat(savedMedicalRecord).isNotNull();
        assertEquals(newMedicalRecord, savedMedicalRecord);
    }

    @Test
    public void canNotSaveExistingMedicalRecordTest() {
        // GIVEN
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName("Jacob");
        newMedicalRecord.setLastName("Boyd");
        // WHEN
        // THEN
        assertThat(medicalRecordRepository.save(newMedicalRecord)).isFalse();
    }


    @Test
    public void canNotSaveNullMedicalRecordTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(medicalRecordRepository.save(null)).isFalse();
    }

    @Test
    public void updateMedicalRecordWithSuccessTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName(firstName);
        newMedicalRecord.setLastName(lastName);
        newMedicalRecord.setBirthdate("01/01/2010");
        newMedicalRecord.setAllergies(new ArrayList<>());
        newMedicalRecord.setMedications(new ArrayList<>());
        // WHEN
        Boolean isUpdated = medicalRecordRepository.updateByFullName(newMedicalRecord);
        MedicalRecord updatedMedicalRecord = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);
        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(updatedMedicalRecord).isNotNull();
        assertEquals(newMedicalRecord, updatedMedicalRecord);
    }


    @Test
    public void canNotUpdateNonExistingMedicalRecordTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Lee";
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName(firstName);
        newMedicalRecord.setLastName(lastName);
        newMedicalRecord.setBirthdate("01/01/2010");
        // WHEN
        Boolean isUpdated = medicalRecordRepository.updateByFullName(newMedicalRecord);
        // THEN
        assertThat(isUpdated).isFalse();
    }

    @Test
    public void deleteExistingMedicalRecordTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName(firstName);
        newMedicalRecord.setLastName(lastName);
        // WHEN
        // THEN
        assertThat(medicalRecordRepository.deleteByFullName(newMedicalRecord)).isTrue();
    }
    @Test
    public void canNotDeleteNonExistingMedicalRecordTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Lee";
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setFirstName(firstName);
        newMedicalRecord.setLastName(lastName);
        // WHEN
        // THEN
        assertThat(medicalRecordRepository.deleteByFullName(newMedicalRecord)).isFalse();
    }
}
