package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public boolean createPerson(Person person) {
        return personRepository.save(person);
    }

    public boolean updatePerson(Person person) {
        return personRepository.updateByFullName(person);
    }

    public boolean deletePerson(Person person) {
        return personRepository.deleteByFullName(person.getFirstName(), person.getLastName());
    }

    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }
}
