package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        produces = "application/json")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping(value = "/person")
    public Person create(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PatchMapping
    @RequestMapping(value = "/person", method = RequestMethod.PATCH)
    public Person update(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public void delete(@RequestBody Person person) {
        personService.deletePerson(person);
    }

    @GetMapping
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getAll() {
        return personService.getAllPersons();
    }

}
