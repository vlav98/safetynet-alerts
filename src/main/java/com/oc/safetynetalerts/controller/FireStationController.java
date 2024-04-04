package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.FireStation;
import com.oc.safetynetalerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = "application/json")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    private static final Logger logger = LogManager.getLogger(FireStationController.class.getName());

    @PostMapping(value = "/fireStation")
    public ResponseEntity<?> create(@RequestBody FireStation fireStation) {
        logger.info("Received POST Request : /fireStation with Request Body : " + fireStation.toString());
        if (fireStationService.createFireStation(fireStation)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(fireStation);
        } else {
            return ResponseEntity.badRequest().body("Can not add this fire station: this fire station you are trying to add is a duplicate.");
        }
    }

    @PatchMapping(value = "/fireStation")
    public ResponseEntity<?> update(@RequestBody FireStation fireStation) {
        logger.info("Received PATCH Request : /fireStation with Request Body : " + fireStation.toString());
        if (fireStationService.updateFireStation(fireStation)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(fireStation);
        } else {
            return ResponseEntity.badRequest().body("Can not edit this fire station: this fire station you are trying to update doesn't exist.");
        }
    }

    @DeleteMapping(value = "/fireStation")
    public void delete(@RequestBody FireStation fireStation) {
        logger.info("Received DELETE Request : /fireStation with Request Body : " + fireStation.toString());
        fireStationService.deleteFireStation(fireStation);
    }

    @GetMapping(value = "/fireStation")
    public List<FireStation> getFireStations(@RequestParam Optional<Integer> stationNumber) {
        logger.info("Received GET Request : /fireStation with Request Param : " + stationNumber.toString());
        return fireStationService.getAllFireStations(stationNumber);
    }

}
