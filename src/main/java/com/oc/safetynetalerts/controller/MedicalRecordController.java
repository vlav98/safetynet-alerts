package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.MedicalRecord;
import com.oc.safetynetalerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        produces = "application/json")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class.getName());

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<?> create(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Received POST Request : /medicalRecord with Request Body : " + medicalRecord.toString());
        if (medicalRecordService.createMedicalRecord(medicalRecord)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(medicalRecord);
        } else {
            return ResponseEntity.badRequest().body("Can not add this medical record: this medical record you are trying to add is a duplicate.");
        }
    }

    @PatchMapping
    @RequestMapping(value = "/medicalRecord", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Received PATCH Request : /medicalRecord with Request Body : " + medicalRecord.toString());
        if (medicalRecordService.updateMedicalRecord(medicalRecord)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(medicalRecord);
        } else {
            return ResponseEntity.badRequest().body("Can not edit this medical record: the medical record you are trying to update doesn't exist.");
        }
    }

    @DeleteMapping
    @RequestMapping(value = "/medicalRecord", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Received DELETE Request : /medicalRecord with Request Body : " + medicalRecord.toString());
        if (medicalRecordService.deleteMedicalRecord(medicalRecord)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.badRequest().body("Can not delete this medical record: the medical record you are trying to delete doesn't exist.");
        }
    }

    @GetMapping
    @RequestMapping(value = "/medicalRecord", method = RequestMethod.GET)
    public List<MedicalRecord> getAll() {
        logger.info("Received GET Request : /medicalRecord");
        return medicalRecordService.getAllMedicalRecords();
    }
}
