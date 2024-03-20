package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.service.PersonService;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        produces = "application/json")
public class PersonController {
    @Autowired
    private PersonService personService;

    private static final Logger logger = LogManager.getLogger("PersonController");

    @PostMapping(value = "/person")
    public ResponseEntity<Person> create(@RequestBody Person person) throws BadRequestException {
        logger.info("Received POST Request : /person");
        if (personService.createPerson(person)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(person);
        } else {
            throw new BadRequestException("Can not add this person: the person you are trying to add is a duplicate.");
        }
    }

    @PatchMapping
    @RequestMapping(value = "/person", method = RequestMethod.PATCH)
    public Person update(@RequestBody Person person) {
        logger.info("Received PATCH Request : /person");
        return personService.updatePerson(person);
    }

    @DeleteMapping
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public void delete(@RequestBody Person person) {
        logger.info("Received DELETE Request : /person");
        personService.deletePerson(person);
    }

    @GetMapping
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getAll() {
        logger.info("Received GET Request : /person");
        return personService.getAllPersons();
    }

}
