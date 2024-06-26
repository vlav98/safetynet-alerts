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

    private static final Logger logger = LogManager.getLogger(PersonController.class.getName());

    @PostMapping(value = "/person")
    public ResponseEntity<?> create(@RequestBody Person person) throws BadRequestException {
        logger.info("Received POST Request : /person with Request Body : " + person.toString());
        if (personService.createPerson(person)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(person);
        } else {
            return ResponseEntity.badRequest().body("Can not add this person: the person you are trying to add is a duplicate.");
        }
    }

    @PatchMapping
    @RequestMapping(value = "/person", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@RequestBody Person person) throws BadRequestException {
        logger.info("Received PATCH Request : /person with Request Body : " + person.toString());
        if (personService.updatePerson(person)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(person);
        } else {
            return ResponseEntity.badRequest().body("Can not edit this person: the person you are trying to update doesn't exist.");
        }
    }

    @DeleteMapping
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Person person) {
        logger.info("Received DELETE Request : /person with Request Body : " + person.toString());
        if (personService.deletePerson(person)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.badRequest().body("Can not delete this person: the person you are trying to delete doesn't exist.");
        }
    }

    @GetMapping
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public List<Person> getAll() {
        logger.info("Received GET Request : /person");
        return personService.getAllPersons();
    }

}
