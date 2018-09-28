package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SearchService;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
@Api(value="Search Controller",description = "검색 API")

public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    PlaceService placeService;

    @GetMapping(value = "/{region}")
    @ApiOperation(value="Search Region",notes = "지역 검색")
    public ResponseEntity<Search> searchwhere(@PathVariable String region){
        Search search = searchService.SearchRegion(region).orElseThrow(()->new NoSuchDataException("서비스 준비중입니다."));
        return new ResponseEntity<>(search, HttpStatus.OK);
    }

    @GetMapping(value="/makeSearchDbToPlaceDb")
    @ApiOperation(value="Make Search DB",notes = "장소 DB를 Search DB에 저장합니다. 주변 인근 지역을 추천하기 위해 따로 두었습니다.")
    public void makePlaceDB(){
        List<Place> placeList = placeService.getAllPlace();
        searchService.MakeSearchDB(placeList);
    }

}
