package com.oc.safetynetalerts.controller;

import com.oc.safetynetalerts.model.FireStation;
import com.oc.safetynetalerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    @PostMapping(value = "/fireStation")
    public ResponseEntity<?> create(@RequestBody FireStation fireStation) {
        if (fireStationService.createFireStation(fireStation)) {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(fireStation);
        } else {
            return ResponseEntity.badRequest().body("Can not add this fire station: this fire station you are trying to add is a duplicate.");
        }
    }

    @PatchMapping(value = "/fireStation")
    public ResponseEntity<?> update(@RequestBody FireStation fireStation) {
        if (fireStationService.updateFireStation(fireStation)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(fireStation);
        } else {
            return ResponseEntity.badRequest().body("Can not edit this fire station: this fire station you are trying to update doesn't exist.");
        }
    }

    @DeleteMapping(value = "/fireStation")
    public void delete(@RequestBody FireStation fireStation) {
        fireStationService.deleteFireStation(fireStation);
    }

    @GetMapping(value = "/fireStation")
    public List<FireStation> getAll(@RequestParam Integer station) {
        return fireStationService.getAllFireStations(station);
    }

}
