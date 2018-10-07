package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Place;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PlaceService {
    List<Place> getAllPlace();
    Optional<Place>findPlaceByNum(int num);
    Page<Place> findPlaceByName(Pageable pageable, String name);
    void updatePlace(Place place);
}
