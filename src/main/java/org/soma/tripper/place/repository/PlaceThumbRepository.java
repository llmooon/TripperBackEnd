package org.soma.tripper.place.repository;

import org.soma.tripper.place.entity.PlaceThumb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceThumbRepository extends JpaRepository<PlaceThumb,Integer> {
    Optional<PlaceThumb> findByThumbnum(int num);
}
