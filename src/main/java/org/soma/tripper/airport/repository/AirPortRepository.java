package org.soma.tripper.airport.repository;

import org.soma.tripper.airport.Entity.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirPortRepository extends JpaRepository<Airport,String> {
    Page<Airport> findAirportByKrcityContaining(Pageable pageable, String str);
}
