package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.ChildAlertInfo;
import com.oc.safetynetalerts.model.HouseholdByAddress;
import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.model.ResidentInfo;
import com.oc.safetynetalerts.service.AlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
public class AlertController {

    @Autowired
    private AlertService alertService;
    private static final Logger logger = LogManager.getLogger(FireStationController.class.getName());

    /**
     * Get a list of children residing at a specific address
     * to send emergency text messages to specific households
     * @param address
     * @return List of Child Alert Information
     */
    @GetMapping(value = "/childAlert")
    public ResponseEntity<List<ChildAlertInfo>> getChildByAddress(@RequestParam String address) {
        logger.info("Received GET Request : /childAlert with Request Param address: " + address);
        List<ChildAlertInfo> childAlertInfosList = alertService.getChildrenList(address);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(childAlertInfosList);
    }

    /**
     * Get a list of phone number of residents served by a fire station
     * to send emergency text messages to specific households
     * @param stationNumber
     * @return a List of Phone Numbers
     */
    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<?> getPhoneNumberByStationNumber(@RequestParam Integer stationNumber) {
        logger.info("Received GET Request : /phoneAlert with Request Param station number: " + stationNumber);
        List<String> phoneList = alertService.getPhoneList(stationNumber);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(phoneList);
    }

    /**
     * Get a list of people living at given address with the number of fire station serving it
     * It must include the name, phone, age and medical history (medication, dosage, allergies)
     * @param address
     * @return a list of people
     */
    @GetMapping(value = "/fire")
    public ResponseEntity<?> getResidentListAtAddress(@RequestParam String address) {
        logger.info("Received GET Request : /fire with Request Param address: " + address);
        List<ResidentInfo> residentInfos = alertService.getResidentList(address);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(residentInfos);
    }

    /**
     * Returns a list of all households served by the fire station.
     * This list should group people by address.
     * It must include the name, phone, age and medical history
     * (medication, dosage, allergies) next to each name
     * @param stations
     * @return a list of household
     */
    @GetMapping(value = "/flood/stations")
    public ResponseEntity<?> getHouseholdsServedByDistrict (@RequestParam List<Integer> stations) {
        logger.info("Received GET Request : /flood/stations with Request Param stations: " + stations.toString());
        List<HouseholdByAddress> householdsList = alertService.getHouseholdsServedByDistrict(stations);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(householdsList.toString());
    }

    /**
     * Returns the name, address, age, email and medical history of each resident
     * If several people has the same name they must all appear
     * @param firstName
     * @param lastName
     * @return a resident information
     */
    @GetMapping(value = "/personInfo")
    public ResponseEntity<?> getPersonInformation(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Received GET Request : /personInfo with Request Param first name: " + firstName + " and last name :" + lastName);
        ResidentInfo personInfo = alertService.getPersonInformation(firstName, lastName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(personInfo);
    }

    /**
     *
     * @param city
     * @return a list of Email of every resident of a city
     */
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<?> getCommunityEmailByCity(@RequestParam String city) {
        logger.info("Received GET Request : /community with Request Param city: " + city);
        List<String> emailList = alertService.getEmailListByCity(city);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(emailList);
    }

}
