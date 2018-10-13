package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.PlaceThumb;
import org.soma.tripper.place.repository.PlaceThumbRepository;
import org.soma.tripper.review.entity.Thumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceThumbServiceImpl implements PlaceThumbService {

    @Autowired
    PlaceThumbRepository placeThumbRepository;

    @Override
    public Optional<PlaceThumb> findThumbByNum(int num) {
        return placeThumbRepository.findByThumbnum(num);
    }

    @Override
    public PlaceThumb save(PlaceThumb placeThumb) {
        return placeThumbRepository.save(placeThumb);
    }
}
