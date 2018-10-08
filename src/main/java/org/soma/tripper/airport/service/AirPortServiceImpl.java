package org.soma.tripper.airport.service;

import org.soma.tripper.airport.Entity.Airport;
import org.soma.tripper.airport.repository.AirPortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirPortServiceImpl implements AirPortService {
    @Autowired
    AirPortRepository airPortRepository;

    @Override
    public void saveCode(Airport airport) {
        airPortRepository.save(airport);
    }
}
