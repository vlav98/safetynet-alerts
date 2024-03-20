package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.MedicalRecord;
import com.oc.safetynetalerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        produces = "application/json")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping(value = "/medicalRecord")
    public MedicalRecord create(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PatchMapping
    @RequestMapping(value = "/medicalRecord", method = RequestMethod.PATCH)
    public MedicalRecord update(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping
    @RequestMapping(value = "/medicalRecord", method = RequestMethod.DELETE)
    public void delete(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteMedicalRecord(medicalRecord);
    }

    @GetMapping
    @RequestMapping(value = "/medicalRecords", method = RequestMethod.GET)
    public List<MedicalRecord> getAll() {
        return medicalRecordService.getAllMedicalRecords();
    }
}
