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

    public boolean save(Person person) {
        if (person != null && persons.stream()
                .filter(filteredPerson -> filteredPerson.getFirstName().equals(person.getFirstName())
                        && filteredPerson.getLastName().equals(person.getLastName()))
                .findFirst().isEmpty()) {
            persons.add(person);
            return true;
        }
        return false;
    }

    public boolean updateByFullName(Person person) {
        int searchedPersonIndex = persons.indexOf(findPeopleByFullName(person.getFirstName(), person.getLastName()));
        if (searchedPersonIndex != -1) {
            persons.set(searchedPersonIndex, person);
            return true;
        }
        return false;
    }

    public boolean deleteByFullName(String firstName, String lastName) {
        int searchedPersonIndex = persons.indexOf(findPeopleByFullName(firstName, lastName));
        if (searchedPersonIndex != -1) {
            persons.remove(searchedPersonIndex);
            return true;
        }
        return false;
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public List<Person> findByAddress(String address) {
        return persons.stream()
                .filter(filteredPerson -> filteredPerson.getAddress().equals(address))
                .toList();
    }

    public List<Person> findPersonsByCity(String city) {
        return persons.stream()
                .filter(filteredPerson -> filteredPerson.getCity().equals(city))
                .toList();
    }
}
