package com.oc.safetynetalerts;

import com.oc.safetynetalerts.model.RawData;
import com.oc.safetynetalerts.repository.MedicalRecordRepository;
import com.oc.safetynetalerts.repository.PersonRepository;
import com.oc.safetynetalerts.utils.ReadDataFromFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SafetynetAlertsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SafetynetAlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RawData rawData = ReadDataFromFile.getData();
        System.out.println(rawData);
        PersonRepository.persons = rawData.getPersons();
        MedicalRecordRepository.medicalRecords = rawData.getMedicalRecords();
    }
}
