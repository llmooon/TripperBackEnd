package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SearchService;
import org.soma.tripper.place.Service.SeqService;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Search;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.schedule.dto.BasicPlaceDTO;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    ReviewService reviewService;

    @Autowired
    SeqService seqService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/region/{region}")
    @ApiOperation(value="Search Region",notes = "지역 검색")
    public ResponseEntity<List<Search>> searchwhere(@PathVariable String region){
        List<Search> search = searchService.SearchRegion(region).orElseThrow(()->new NoSuchDataException("서비스 준비중입니다."));
        return new ResponseEntity<>(search,HttpStatus.OK);
    }

    @GetMapping(value="/place/{place}/{page}")
    @ApiOperation(value="search place", notes="장소 이름 입력/ 페이지 입력")
    public ResponseEntity<List<BasicPlaceDTO>> searchPlace(@PathVariable String place, @PathVariable int page){
        int size = 20;
        PageRequest request;
        request=PageRequest.of(page, size);
        List<Place> result = placeService.findPlaceByName(request,place).orElseThrow(()->new NoSuchDataException("서비스 준비중입니다."));
        List<BasicPlaceDTO> results=new ArrayList<>();
        for (Place p:result ) {
            results.add(
                    BasicPlaceDTO.builder()
                    .placenum(p.getPlace_num())
                    .name(p.getName())
                    .city(p.getCity())
                    .picture(p.getThumb().getBucket())
                    .build()
            );
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }

    @GetMapping(value="/review/{review}/{page}")
    @ApiOperation(value="search review")
    public ResponseEntity<List<MainReviewDTO>> searchReview(@PathVariable String review, @PathVariable int page){
        PageRequest request=PageRequest.of(page,20);
        Page<Review> res = reviewService.loadReviewByStr(review,request);
        List<MainReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review r: res){
            reviewDTOS.add(
                    MainReviewDTO.builder()
                    .reviewnum(r.getReviewnum())
                    .photo(r.getThumb().getBucket())
                    .time(r.getCreatedDate())
                    .writer(userService.findUserByUsernum(r.getUsernum()).get().getEmail())
                    .title(seqService.loadSeq(r.getSeqnum()).get().getTitle())
                    .build());
        }

        return new ResponseEntity<>(reviewDTOS,HttpStatus.OK);

    }


    @GetMapping(value="/makeSearchDbToPlaceDb")
    @ApiOperation(value="Make Search DB",notes = "장소 DB를 Search DB에 저장합니다. 주변 인근 지역을 추천하기 위해 따로 두었습니다.")
    public void makePlaceDB(){
        List<Place> placeList = placeService.getAllPlace();
        searchService.MakeSearchDB(placeList);
    }

}
