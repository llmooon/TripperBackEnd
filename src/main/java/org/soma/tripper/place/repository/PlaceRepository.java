package org.soma.tripper.place.repository;

import org.soma.tripper.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Integer> {
    List<Place> findAll();
}
