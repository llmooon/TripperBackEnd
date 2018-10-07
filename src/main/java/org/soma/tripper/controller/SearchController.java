package org.soma.tripper.controller;

import com.amazonaws.services.xray.model.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SearchService;
import org.soma.tripper.place.dto.SimplePlaceDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Search;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
@Api(value="Search Controller",description = "검색 API")

public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    PlaceService placeService;

    @GetMapping(value = "/region/{region}")
    @ApiOperation(value="Search Region",notes = "지역 검색")
    public ResponseEntity<List<Search>> searchwhere(@PathVariable String region){
        List<Search> search = searchService.SearchRegion(region).orElseThrow(()->new NoSuchDataException("서비스 준비중입니다."));
        return new ResponseEntity<>(search,HttpStatus.OK);
    }

    @GetMapping(value="/place/{place}/{page}")
    @ApiOperation(value="search place", notes="장소 이름 입력/ 페이지 입력")
    public ResponseEntity<List<SimplePlaceDTO>> searchPlace(@PathVariable String place, @PathVariable int page){
        int size = 20;
        PageRequest request;
        request=PageRequest.of(page, size);
        Page<Place> result = placeService.findPlaceByName(request,place);
        List<SimplePlaceDTO> results=new ArrayList<>();
        for (Place p:result ) {
            results.add(
                SimplePlaceDTO.builder()
                    .name(p.getName())
                    .location(p.getCity())
                    .thumb(p.getThumb().getBucket())
                    .build()
            );
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }

    @GetMapping(value="/makeSearchDbToPlaceDb")
    @ApiOperation(value="Make Search DB",notes = "장소 DB를 Search DB에 저장합니다. 주변 인근 지역을 추천하기 위해 따로 두었습니다.")
    public void makePlaceDB(){
        List<Place> placeList = placeService.getAllPlace();
        searchService.MakeSearchDB(placeList);
    }

}
