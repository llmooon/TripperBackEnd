package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    @Override
    public List<Place> getAllPlace() {
        return placeRepository.findAll();
    }
}
