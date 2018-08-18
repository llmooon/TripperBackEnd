package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    List<Place> getAllPlace();
    Optional<Place>findPlaceByNum(int num);
}
