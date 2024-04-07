package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.Person;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PersonRepositoryTests {

    @InjectMocks
    private PersonRepository personRepository;

    @Mock
    private PersonRepository mockedPersonRepository;

    private List<Person> personList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        for (int i = 0; i<3; i++) {
            Person person = getPerson(i, "A" + i, "B" + i);
            personList.add(person);
        }
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

    @Test
    public void findPeopleByFullName_whenPersonExists_returnPerson() {
//        // GIVEN
//        Person expectedPerson = getPerson(1, "John", "Smith");
//        personList.add(expectedPerson);
//        System.out.println(personList);
//
//        // WHEN
//        Person foundPerson = personRepository.findPeopleByFullName("John", "Smith");
//
//        System.out.println(foundPerson);
//        // THEN
//        assertEquals(expectedPerson, foundPerson);
    }


    @Test
    public void savePersonWithSuccessTest() {
        // GIVEN
        Person newPerson = getPerson(3, "A3", "B3");

        // WHEN
        when(mockedPersonRepository.getAllPersons()).thenReturn(personList);

        // THEN
        assertThat(personRepository.save(newPerson)).isTrue();
//        assert(personList.contains(newPerson));
    }
    @Test
    public void canNotSaveNullTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(personRepository.save(null)).isFalse();
    }
    @Test
    public void canNotUpdateNonExistentPersonTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Smith";
        Person person = getPerson(3, firstName, lastName);

        // WHEN
        when(mockedPersonRepository.findPeopleByFullName(firstName, lastName)).thenReturn(null);

        // THEN
        assertThat(personRepository.updateByFullName(person)).isFalse();
    }

    @Test
    public void getAllPersonsTest() {
        // GIVEN
        // WHEN
        List<Person> allPersonList = personRepository.getAllPersons();
        // THEN
        assertThat(allPersonList).isNotNull();
        System.out.println(allPersonList);
        assertThat(allPersonList.contains(getPerson(0, "A0", "B0"))).isTrue();
    }

}
