package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.Person;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class PersonRepositoryTests {

    PersonRepository personRepository = new PersonRepository();

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
    public void findPeopleByFullNameWhenPersonExistsReturnPerson() {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        Person expectedPerson =  getPerson(1, firstName, lastName);
        // WHEN
        Person foundPerson = personRepository.findPeopleByFullName(firstName, lastName);

        // THEN
        assertEquals(expectedPerson, foundPerson);
        assertThat(foundPerson).isNotNull();
    }


    @Test
    public void savePersonWithSuccessTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Smith";
        Person newPerson = getPerson(1, firstName, lastName);
        // WHEN
        // THEN
        assertThat(personRepository.save(newPerson)).isTrue();
        Person foundPerson = personRepository.findPeopleByFullName(firstName, lastName);
        assertThat(foundPerson).isNotNull();
        assertEquals(newPerson, foundPerson);
    }
    @Test
    public void canNotSavePersonWithNullValueTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(personRepository.save(null)).isFalse();
    }

    @Test
    public void canNotSavePersonWhenPersonExistsTest() {
        // GIVEN
        Person newPerson = getPerson(1, "John", "Boyd");
        // WHEN
        // THEN
        assertThat(personRepository.save(newPerson)).isFalse();
    }

    @Test
    public void updateExistingPersonTest() {
        // GIVEN
        String firstName = "John";
        String lastName = "Boyd";
        Person person = getPerson(1, firstName, lastName);
        // WHEN
        Boolean isUpdated = personRepository.updateByFullName(person);
        Person updatedPerson = personRepository.findPeopleByFullName(firstName, lastName);
        // THEN
        assertThat(isUpdated).isTrue();
        assertEquals(person, updatedPerson);
    }

    @Test
    public void canNotUpdateNonExistentPersonTest() {
        // GIVEN
        Person person = getPerson(1, "Ally", "Smith");

        // WHEN
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
    }

    @Test
    public void deleteExistingPersonTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(personRepository.deleteByFullName("John", "Boyd")).isTrue();
    }


    @Test
    public void deleteNonExistingPersonTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(personRepository.deleteByFullName("John", "John")).isFalse();
    }

    @Test
    public void findPersonWithNonExistingAddress() {
        // GIVEN
        // WHEN
        List<Person> resultList = personRepository.findByAddress("");
        // THEN
        assertThat(resultList).isEmpty();
    }

    @Test
    public void findPersonWithExistingAddress() {
        // GIVEN
        // WHEN
        List<Person> resultList = personRepository.findByAddress("1509 Culver St");
        // THEN
        assertThat(resultList).isNotEmpty();
    }

    @Test
    public void findPersonWithNonExistentCity() {
        // GIVEN
        // WHEN
        List<Person> resultList = personRepository.findPersonsByCity("");
        // THEN
        assertThat(resultList).isEmpty();
    }

    @Test
    public void findPersonByCity() {
        // GIVEN
        // WHEN
        List<Person> resultList = personRepository.findPersonsByCity("Culver");
        // THEN
        assertThat(resultList).isNotEmpty();
    }
}
