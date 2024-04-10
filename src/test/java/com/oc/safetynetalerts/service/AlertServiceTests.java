package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.FireStation;
import com.oc.safetynetalerts.model.MedicalRecord;
import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.repository.FireStationRepository;
import com.oc.safetynetalerts.repository.MedicalRecordRepository;
import com.oc.safetynetalerts.repository.PersonRepository;
import com.oc.safetynetalerts.utils.CalculateAgeUtil;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertServiceTests {

    @MockBean
    private AlertService alertService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @MockBean
    private FireStationRepository fireStationRepository;

    @MockBean
    private CalculateAgeUtil calculateAgeUtilMock;

    @BeforeAll
    static void setUp() {
    }
    

    private static Person getPerson(int i, String firstName, String lastName) {
        String address = String.valueOf(i);
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

//    @Test
//    public void getChildrenListSuccessTest() throws Exception{
//        // GIVEN
//
//        String testAddress = "10 aaa";
//
//        List<Person> personList = new ArrayList<>();
//        List<MedicalRecord> medicalRecordList = new ArrayList<>();
//        for (int i=0; i<4; i++) {
//            String firstName = "A" + String.valueOf(i).toUpperCase() + "a";
//            String lastName = "B" + String.valueOf(i).toUpperCase() + "b";
//            Person person = getPerson(i, firstName, lastName);
//            personList.add(person);
//
//            MedicalRecord medicalRecord = new MedicalRecord();
//            medicalRecord.setFirstName(firstName);
//            medicalRecord.setLastName(lastName);
//            medicalRecord.setBirthdate("01-01-2015");
//            medicalRecord.setAllergies(new ArrayList<>());
//            medicalRecord.setMedications(new ArrayList<>());
//            medicalRecordList.add(medicalRecord);
//        }
//
//        // WHEN
//        when(personRepository.findByAddress(testAddress)).thenReturn(personList);
//
//        when(CalculateAgeUtil.calculateAge(String.valueOf(any(LocalDate.class)), any(LocalDate.class))).thenReturn(10);
//        when(medicalRecordRepository.findMedicalRecordByFullName("A0a", "B0b")).thenReturn(medicalRecordList.get(0));
//        when(medicalRecordRepository.findMedicalRecordByFullName("A1a", "B1b")).thenReturn(medicalRecordList.get(1));
//        when(medicalRecordRepository.findMedicalRecordByFullName("A2a", "B2b")).thenReturn(medicalRecordList.get(2));
//
//        // THEN
//        System.out.println(medicalRecordList.get(0));
//        assertThat(alertService.getChildrenList(testAddress)).isEqualTo("A1a");
//        assertThat(alertService.getChildrenList(testAddress).get(1).getFirstName()).isEqualTo("A2a");
//    }
//
//    @Test
//    public void getChildrenListWithEmptyPersonListTest() throws Exception{
//        // GIVEN
//        String testAddress = "10 aaa";
//
//        // WHEN
//        when(personRepository.findByAddress(testAddress)).thenReturn(null);
//
//        // THEN
//        assertThat(alertService.getChildrenList(testAddress)).isNull();
//    }
//
//    @Test
//    public void getChildrenListWithEmptyMedicalRecordListTest() throws Exception{
//        // GIVEN
//        String testAddress = "10 aaa";
//
//        // WHEN
//        when(personRepository.findByAddress(testAddress)).thenReturn(null);
//
//        // THEN
//        assertThat(alertService.getChildrenList(testAddress)).isNull();
//    }
//
//    @Test
//    public void getPhoneListWithNoFireStationTest() throws Exception {
//        // GIVEN
//        Integer stationNumber = 3;
//        List<FireStation> fireStationList = new ArrayList<>();
//
//        // WHEN
//        when(fireStationRepository.findFireStationByStationNumber(stationNumber)).thenReturn(fireStationList);
//
//        // THEN
//        assertThat(alertService.getPhoneList(stationNumber));
//    }
//
//    @Test
//    public void getPhoneListTest() throws Exception {
//        // GIVEN
//        Integer stationNumber = 2;
//        FireStation fireStation1 = new FireStation();
//        fireStation1.setStation(1);
//        fireStation1.setAddress("Address 1");
//        FireStation fireStation2 = new FireStation();
//        fireStation2.setStation(2);
//        fireStation2.setAddress("Address 2");
//        List<FireStation> fireStationList = Arrays.asList(fireStation1, fireStation2);
//        Person person1 = getPerson(1, "A1", "B1");
//        person1.setAddress("Address 1");
//        Person person2 = getPerson(2, "A2", "B2");
//        person2.setAddress("Address 2");
//
//        // WHEN
//        when(fireStationRepository.findFireStationByStationNumber(stationNumber)).thenReturn(fireStationList);
//        when(personRepository.findByAddress("Address 1")).thenReturn(List.of(person1));
//        when(personRepository.findByAddress("Address 2")).thenReturn(List.of(person2));
//
//        // THEN
//        List<String> phoneList = alertService.getPhoneList(stationNumber);
//        System.out.println(person1.toString());
//        System.out.println(person2.toString());
//        System.out.println(phoneList);
//        assertThat(phoneList).isNotNull();
//        assert(phoneList.contains("111-111-111-1111"));
//        assert(phoneList.contains("222-222-222-2222"));
//    }
}
