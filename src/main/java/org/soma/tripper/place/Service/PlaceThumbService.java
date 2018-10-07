package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.PlaceThumb;
import org.soma.tripper.review.entity.Thumb;

import java.util.Optional;

public interface PlaceThumbService {
    Optional<PlaceThumb> findThumbByNum(int num);
}
