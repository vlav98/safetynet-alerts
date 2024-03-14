package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {
    // TODO: ça sera un DAO
    public static List<Person> persons;

    public Person findPeopleByFullName(String firstName, String lastName) {
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
    }

    public Person save(Person person) {
        persons.add(person);
        return person;
    }

    public Person updateByFullName(Person person) {
        int searchedPersonIndex = persons.indexOf(findPeopleByFullName(person.getFirstName(), person.getLastName()));
        if (searchedPersonIndex != -1) {
            System.out.println("index: "+ searchedPersonIndex);
            persons.set(searchedPersonIndex, person);
        }
        return persons.get(searchedPersonIndex);
    }

    public void deleteByFullName(Person person) {
        int searchedPersonIndex = persons.indexOf(findPeopleByFullName(person.getFirstName(), person.getLastName()));
        if (searchedPersonIndex != -1) {
            persons.remove(searchedPersonIndex);
        }
    }

    public List<Person> getAllPersons() {
        return persons;
    }
}
