package com.oc.safetynetalerts.repository;

import com.oc.safetynetalerts.model.FireStation;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FireStationRepository {
    public static List<FireStation> fireStations;

    public List<FireStation> findFireStationByStationNumber(Integer stationNumber) {
        return fireStations.stream()
                .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .toList();
    }

    public FireStation findFireStationByAddress(String address) {
        return fireStations.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findAny().orElse(null);
    }

    public boolean save(FireStation fireStation) {
        if (fireStation != null && fireStations.stream()
                .filter(filteredFireStation -> filteredFireStation.getAddress().equals(fireStation.getAddress()))
                .findFirst()
                .isEmpty()) {
            fireStations.add(fireStation);
            return true;
        }
        return false;
    }

    public boolean updateStationNumberByAddress(FireStation fireStation) {
        int searchedFireStationIndex = fireStations.indexOf(findFireStationByAddress(fireStation.getAddress()));
        if (searchedFireStationIndex != -1) {
            fireStations.set(searchedFireStationIndex, fireStation);
            return true;
        }
        return false;
    }

    public boolean deleteByAddress(String address) {
        int searchedFireStationIndex = fireStations.indexOf(findFireStationByAddress(address));
        if (searchedFireStationIndex != -1) {
            fireStations.remove(searchedFireStationIndex);
            return true;
        }
        return false;
    }

    public List<FireStation> getFireStations(Optional<Integer> station) {
        return fireStations.stream()
                .filter(fireStation -> station.isEmpty() ||
                        station.get().equals(fireStation.getStation())).toList();
    }
}
