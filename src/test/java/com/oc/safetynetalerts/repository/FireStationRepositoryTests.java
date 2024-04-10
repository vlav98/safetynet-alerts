package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.FireStation;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class FireStationRepositoryTests {

    FireStationRepository fireStationRepository = new FireStationRepository();

    @Test
    public void findFireStationByStationNumberWhenFireStationExistsReturnList() {
        // GIVEN
        // WHEN
        List<FireStation> resultList = fireStationRepository.findFireStationByStationNumber(1);
        // THEN
        assertThat(resultList).isNotEmpty();
    }


    @Test
    public void findFireStationByAddressWhenFireStationExistsReturnFireStation() {
        // GIVEN
        // WHEN
        FireStation fireStationResult = fireStationRepository.findFireStationByAddress("1509 Culver St");
        // THEN
        assertThat(fireStationResult).isNotNull();
    }

    @Test
    public void saveFireStationWithSuccessTest() {
        // GIVEN
        FireStation newFireStation = new FireStation();
        newFireStation.setStation(2);
        newFireStation.setAddress("New Address");
        // WHEN
        // THEN
        assertThat(fireStationRepository.save(newFireStation)).isTrue();
        FireStation savedFireStation = fireStationRepository.findFireStationByAddress("New Address");
        assertThat(savedFireStation).isNotNull();
        assertEquals(newFireStation, savedFireStation);
    }

    @Test
    public void canNotSaveExistingFireStationTest() {
        // GIVEN
        FireStation newFireStation = new FireStation();
        newFireStation.setStation(0);
        newFireStation.setAddress("1509 Culver St");
        // WHEN
        // THEN
        assertThat(fireStationRepository.save(newFireStation)).isFalse();
    }

    @Test
    public void canNotSaveNullFireStationTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(fireStationRepository.save(null)).isFalse();
    }

    @Test
    public void updateFireStationWithSuccessTest() {
        // GIVEN
        FireStation newFireStation = new FireStation();
        newFireStation.setStation(2);
        newFireStation.setAddress("1509 Culver St");
        // WHEN
        Boolean isUpdated = fireStationRepository.updateStationNumberByAddress(newFireStation);
        FireStation updatedFireStation = fireStationRepository.findFireStationByAddress("1509 Culver St");
        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(updatedFireStation).isNotNull();
        assertEquals(newFireStation, updatedFireStation);
    }

    @Test
    public void canNotUpdateNonExistingFireStationTest() {
        // GIVEN
        FireStation fireStationToUpdate = new FireStation();
        fireStationToUpdate.setStation(2);
        fireStationToUpdate.setAddress("New Address");
        // WHEN
        // THEN
        assertThat(fireStationRepository.updateStationNumberByAddress(fireStationToUpdate)).isFalse();
    }

    @Test
    public void deleteExistingFireStationTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(fireStationRepository.deleteByAddress("29 15th St")).isTrue();
    }

    @Test
    public void canNotDeleteNonExistingFireStationTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThat(fireStationRepository.deleteByAddress("")).isFalse();
    }

    @Test
    public void getAllFireStationsTest() {
        // GIVEN
        // WHEN
        List<FireStation> allPersonList = fireStationRepository.getFireStations(Optional.empty());
        // THEN
        assertThat(allPersonList).isNotNull();
        assertThat(allPersonList.size()).isGreaterThan(1);
    }


    @Test
    public void getFireStationsByStationTest() {
        // GIVEN
        // WHEN
        List<FireStation> allPersonList = fireStationRepository.getFireStations(Optional.of(2));
        // THEN
        assertThat(allPersonList).isNotNull();
        assertThat(allPersonList.size()).isGreaterThan(1);
    }
}
