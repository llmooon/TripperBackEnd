package org.soma.tripper.place.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Search;
import org.soma.tripper.place.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Optional<List<Search>> SearchRegion(String name){
        return (searchRepository.findByNameContains(name));
    }

    @Override
    public void MakeSearchDB(List<Place> placeList) {
        for(Place place : placeList){
            makeSearch(place.getCity());
            makeSearch(place.getCountry());
        }
    }

    private void makeSearch(String name){
        if(searchRepository.findByName(name)==null){
            searchRepository.save(
                    Search.builder()
                        .name(name)
                        .build());
        }
    }
}
