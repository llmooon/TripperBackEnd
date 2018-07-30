package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Search;

public interface SearchService {
    Search SearchRegion(String city, String country);
}
