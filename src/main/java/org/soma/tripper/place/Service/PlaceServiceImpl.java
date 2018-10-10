package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    @Override
    public List<Place> getAllPlace() {
        return placeRepository.findAll();
    }

    @Override
    public Optional<Place> findPlaceByNum(int num) {
        return placeRepository.findById(num);
    }

    @Override
    public Page<Place> findPlaceByName(Pageable pageable, String name) {
        return placeRepository.findByNameContains(pageable,name);
    }

    @Override
    public void updatePlace(Place place) {
        placeRepository.save(place);
    }

    @Override
    public List<Place> getPlaceByVersion(int version) {
        return placeRepository.getPlaceByType(version);
    }
}
