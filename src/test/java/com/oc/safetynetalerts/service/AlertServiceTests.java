package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.*;
import com.oc.safetynetalerts.repository.FireStationRepository;
import com.oc.safetynetalerts.repository.MedicalRecordRepository;
import com.oc.safetynetalerts.repository.PersonRepository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertServiceTests {
    AlertService alertService = new AlertService();

    public AlertServiceTests() {
        PersonRepository personRepository = new PersonRepository();
        MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();
        FireStationRepository fireStationRepository = new FireStationRepository();
    }

    @BeforeAll
    static void setUp() {
    }
    

    private static Person getPerson(int i, String firstName, String lastName) {
        String address = i + "Imaginary Street";
        String city = String.valueOf(i).repeat(3);
        String zip = String.valueOf(i).repeat(5);
        String phone = String.format("%d-%d-%d-%d", i * 111, i * 111, i * 111, i * 1111);
        String email = String.format("%s@email.com", firstName.toLowerCase());

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPhone(phone);
        person.setAddress(address);
        person.setCity(city);
        person.setZip(zip);
        person.setEmail(email);
        return person;
    }

    @Test
    public void getChildrenListSuccessTest() throws Exception{
        // GIVEN
        String address = "1509 Culver St";

        // WHEN
        List<ChildAlertInfo> childAlertInfosList = alertService.getChildrenList(address);

        // THEN
        assertThat(childAlertInfosList).isNotNull();
        assertThat(childAlertInfosList.size()).isEqualTo(2);
        assertThat(childAlertInfosList.get(0).getLastName()).isEqualTo("Boyd");
        assertThat(childAlertInfosList.get(0).getAge()).isEqualTo(12);
    }

    @Test
    public void getChildrenListWithEmptyPersonListTest() throws Exception{
        // GIVEN
        String address = "Non Existent Address";

        // WHEN
        List<ChildAlertInfo> childAlertInfosList = alertService.getChildrenList(address);

        // THEN
        assertThat(childAlertInfosList).isNull();
    }

    @Test
    public void getNullWithInvalidStationNumberPhoneListTest() throws Exception {
        // GIVEN
        Integer stationNumber = 6;
        // WHEN
        List<String> phoneList = alertService.getPhoneList(stationNumber);

        // THEN
        assertThat(phoneList).isNull();
    }

    @Test
    public void getPhoneListTest() throws Exception {
        // GIVEN
        Integer stationNumber = 3;
        // WHEN
        List<String> phoneList = alertService.getPhoneList(stationNumber);

        // THEN
        assertThat(phoneList).isNotNull();
        assertThat(phoneList.size()).isGreaterThan(2);
    }

    @Test
    public void canNotGetResidentListTest() throws Exception {
        // GIVEN
        String address = "Non Existent Address";
        // WHEN
        List<ResidentInfo> residentInfoList = alertService.getResidentList(address);
        // THEN
        assertThat(residentInfoList).isNull();
    }

    @Test
    public void getResidentListWithSuccessTest() throws Exception {
        // GIVEN
        String address = "1509 Culver St";
        // WHEN
        List<ResidentInfo> residentInfoList = alertService.getResidentList(address);
        // THEN
        assertThat(residentInfoList).isNotNull();
        assertThat(residentInfoList.size()).isGreaterThan(2);
    }

    @Test
    public void getHouseholdsServedByDistrictTest() throws Exception {
        // GIVEN
        List<Integer> stationNumberList = Arrays.asList(0, 1);
        // WHEN
        List<HouseholdByAddress> householdByAddressList = alertService.getHouseholdsServedByDistrict(stationNumberList);
        // THEN
        assertThat(householdByAddressList).isNotNull();
        assertThat(householdByAddressList.size()).isGreaterThan(2);
    }

    @Test
    public void getNullWithInvalidStationNumberHouseholdsServedByDistrictTest() throws Exception {
        // GIVEN
        List<Integer> stationNumberList = new ArrayList<>();
        // WHEN
        List<HouseholdByAddress> householdByAddressList = alertService.getHouseholdsServedByDistrict(stationNumberList);
        // THEN
        assertThat(householdByAddressList).isNull();
    }

    @Test
    public void getPersonInformationTest() throws Exception {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        // WHEN
        ResidentInfo residentInfoResult = alertService.getPersonInformation(firstName, lastName);
        // THEN
        assertThat(residentInfoResult).isNotNull();
        assertThat(residentInfoResult.getLastName()).isEqualTo(lastName);
    }


    @Test
    public void getNullIfNoneIfNotFoundPersonInformationTest() throws Exception {
        // GIVEN
        String firstName = "John";
        String lastName = "Smith";
        // WHEN
        ResidentInfo residentInfoResult = alertService.getPersonInformation(firstName, lastName);
        // THEN
        assertThat(residentInfoResult).isNull();
    }

    @Test
    public void getEmailListByCityTest() throws Exception {
        // GIVEN
        String city = "Culver";
        // WHEN
        List<String> emailList = alertService.getEmailListByCity(city);
        // THEN
        assertThat(emailList).isNotNull();
        assertThat(emailList.size()).isGreaterThan(2);
    }

    @Test
    public void getNullIfCityIsIncorrectEmailListByCityTest() throws Exception {
        // GIVEN
        String city = "Incorrect";
        // WHEN
        List<String> emailList = alertService.getEmailListByCity(city);
        // THEN
        assertThat(emailList).isNull();
    }
}
