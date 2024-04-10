package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.MedicalRecord;
import com.oc.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public boolean createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.updateByFullName(medicalRecord);
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.deleteByFullName(medicalRecord);
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return MedicalRecordRepository.medicalRecords;
    }
}
