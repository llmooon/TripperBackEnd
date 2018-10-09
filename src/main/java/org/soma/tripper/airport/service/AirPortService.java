package org.soma.tripper.airport.service;

import org.soma.tripper.airport.Entity.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirPortService {
    void saveCode(Airport airport);
    Page<Airport> findCode(Pageable pageable, String city);
}
