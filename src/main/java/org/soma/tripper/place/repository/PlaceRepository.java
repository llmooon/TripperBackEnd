package org.soma.tripper.place.repository;

import org.soma.tripper.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place,Integer> {
    List<Place> findAll();
    Page<Place> findByNameContains(Pageable page, String name);
    Page<Place> getPlaceByType(Pageable page,int version);
}
