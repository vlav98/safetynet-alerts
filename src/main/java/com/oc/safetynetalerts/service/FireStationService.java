package com.oc.safetynetalerts.service;

import com.oc.safetynetalerts.model.FireStation;
import com.oc.safetynetalerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireStationService {
    @Autowired
    private FireStationRepository fireStationRepository;

    public boolean createFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    public boolean updateFireStation(FireStation fireStation) {
        return fireStationRepository.updateStationNumberByAddress(fireStation);
    }

    public boolean deleteFireStation(FireStation fireStation) {
        return fireStationRepository.deleteByAddress(fireStation.getAddress());
    }

    public List<FireStation> getAllFireStations(Optional<Integer> station) {
        return fireStationRepository.getFireStations(station);
    }
}
