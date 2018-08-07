package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Search;

import java.util.List;

public interface SearchService {
    Search SearchRegion(String name);
    void MakeSearchDB(List<Place> placeList);
}
