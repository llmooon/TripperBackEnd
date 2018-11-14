package org.soma.tripper.place.Service;

import org.soma.tripper.place.dto.PlaceWithDistance;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.PlaceThumb;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PlaceService {
    List<Place> getAllPlace();
    Optional<Place>findPlaceByNum(int num);
    Page<Place> findPlaceByName(Pageable pageable, String name);
    List<Object[]> getPlaceByVersion(int version, double averageLA, double averageLO, Pageable page);
    void updatePlace(Place place);
    void updatePlaceList(List<Place> places);
    List<Place> findPlaceByThumb(PlaceThumb placeThumb);
}
