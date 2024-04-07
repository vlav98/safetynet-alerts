package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.*;
import com.oc.safetynetalerts.repository.FireStationRepository;
import com.oc.safetynetalerts.repository.MedicalRecordRepository;
import com.oc.safetynetalerts.repository.PersonRepository;
import com.oc.safetynetalerts.utils.CalculateAgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class AlertService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * list of kids residing @ address
     * firstName, lastName, age, list other members
     * no kid -> null
     * @param address
     * @return
     */
    public List<ChildAlertInfo> getChildrenList(String address) {

        List<Person> personsList = personRepository.findByAddress(address);

        if (personsList.isEmpty()) {
            return null;
        }

        List<MedicalRecord> medicalRecordList = personsList.stream()
                .map(person -> medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName()))
                .filter(Objects::nonNull)
                .toList();

        if (medicalRecordList.isEmpty()) {
            return null;
        }

        List<ChildAlertInfo> childAlertList = new ArrayList<>();

        for (MedicalRecord medicalRecord: medicalRecordList) {
            int age = CalculateAgeUtil.calculateAge(medicalRecord.getBirthdate(), LocalDate.now());
            if (age <= 18) {
                List<FamilyMember> familyMembers = new ArrayList<>();
                personsList.stream()
                        .filter(person -> !medicalRecord.getFirstName().equals(person.getFirstName())
                        && medicalRecord.getLastName().equals(person.getLastName()))
                        .forEach(person -> familyMembers.add(FamilyMember.builder()
                                .firstName(person.getFirstName())
                                .lastName(person.getLastName())
                                .phone(person.getPhone())
                                .email(person.getEmail())
                                .build()));
                childAlertList.add(ChildAlertInfo.builder()
                                .firstName(medicalRecord.getFirstName())
                                .lastName(medicalRecord.getLastName())
                                .age(age)
                                .familyMembers(familyMembers)
                        .build());
            }
        }

        return childAlertList;
    }

    /**
     * list of phone number of residents served by a firestation
     */
    public List<String> getPhoneList(Integer stationNumber) {
        List<FireStation> fireStationsList = fireStationRepository.findFireStationByStationNumber(stationNumber);
        if (fireStationsList.isEmpty()) return null;

        List<String> phoneList = new ArrayList<>();

        fireStationsList.stream()
                .map(fireStation -> personRepository.findByAddress(fireStation.getAddress()))
                .flatMap(List::stream)
                .forEach(person -> phoneList.add(person.getPhone()));

        if (phoneList.isEmpty()) return null;

        return phoneList;
    }

    /**
     * return all homes served by a barrack
     * lastName, phone, age, antécédents médicaux
     * @param address
     * @return
     */
    public List<ResidentInfo> getResidentList(String address) {
        List<Person> personList = personRepository.findByAddress(address);
        if (personList.isEmpty()) {
            return null;
        }

        List<ResidentInfo> residentList = new ArrayList<>();

        for (Person person: personList) {
            MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            residentList.add(ResidentInfo.builder()
                    .lastName(person.getLastName())
                    .phone(person.getPhone())
                    .age(CalculateAgeUtil.calculateAge(medicalRecord.getBirthdate(), LocalDate.now()))
                    .medications(medicalRecord.getMedications())
                    .allergies(medicalRecord.getAllergies()).build());
        }

        return residentList;
    }


    public List<HouseholdByAddress> getHouseholdsServedByDistrict(List<Integer> stations) {
        List<FireStation> fireStationList = stations.stream()
                .map(fireStationRepository::findFireStationByStationNumber)
                .flatMap(Collection::stream).toList();

        if (fireStationList.isEmpty()) return null;

        List<HouseholdByAddress> householdList = new ArrayList<>();

        for (FireStation fireStation: fireStationList) {
            List<Person> personList = personRepository.findByAddress(fireStation.getAddress());

            List<ResidentInfo> residentInfoList = new ArrayList<>();

            for (Person person: personList) {
                MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByFullName(person.getFirstName(), person.getLastName());
                residentInfoList.add(ResidentInfo.builder()
                                .lastName(person.getLastName())
                                .phone(person.getPhone())
                                .age(CalculateAgeUtil.calculateAge(medicalRecord.getBirthdate(), LocalDate.now()))
                                .medications(medicalRecord.getMedications())
                                .allergies(medicalRecord.getAllergies())
                        .build());
            }

            householdList.add(HouseholdByAddress.builder()
                    .address(fireStation.getAddress())
                    .resident(residentInfoList)
                    .build());
        }

        return householdList;
    }

    public ResidentInfo getPersonInformation(String firstName, String lastName) {
        Person person = personRepository.findPeopleByFullName(firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordByFullName(firstName, lastName);

        return ResidentInfo.builder()
                .lastName(person.getLastName())
                .phone(person.getPhone())
                .age(CalculateAgeUtil.calculateAge(medicalRecord.getBirthdate(), LocalDate.now()))
                .medications(medicalRecord.getMedications())
                .allergies(medicalRecord.getAllergies())
                .build();
    }


    public List<String> getEmailListByCity(String city) {
        List<String> emailList = new ArrayList<>();

        personRepository.findPersonsByCity(city).forEach(person -> emailList.add(person.getEmail()));

        if (emailList.isEmpty()) return null;

        return emailList;
    }

}
