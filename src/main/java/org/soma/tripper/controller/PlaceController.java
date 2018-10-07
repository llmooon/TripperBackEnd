package org.soma.tripper.controller;

import com.amazonaws.services.xray.model.Http;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.PlaceThumbService;
import org.soma.tripper.place.dto.DetailPlaceDTO;
import org.soma.tripper.place.dto.PlaceReviewDTO;
import org.soma.tripper.place.dto.SimplePlaceDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.PlaceThumb;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.service.DetailsService;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.review.service.ThumbService;
import org.soma.tripper.user.domain.User;
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
@RequestMapping(value="/place")
public class PlaceController {
    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceThumbService thumbService;

    @Autowired
    DetailsService detailsService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/detail/{num}/{page}")
    @ApiOperation(value = "view place detail")
    public ResponseEntity<DetailPlaceDTO> viewPlace(@PathVariable Integer num,@PathVariable Integer page){


        Place place = placeService.findPlaceByNum(num).orElseThrow(()-> new NoSuchDataException("잘못된 placenum"));

        //load review
        int size = 20;
        PageRequest request;
        request=PageRequest.of(page, size);
        Page<Details> details=detailsService.loadDetailsByPlace(request,place);
        List<PlaceReviewDTO> placeReviewDTOS = new ArrayList<>();
        for(Details d:details){
            Review review = reviewService.loadReviewById(d.getReviewnum()).orElseThrow(()-> new NoSuchDataException("error reviewnum"));
            User user = userService.findUserByUsernum(review.getUsernum()).orElseThrow(()-> new NoSuchDataException("error usernum"));
            placeReviewDTOS.add(PlaceReviewDTO.builder()
                                            .name(user.getName())
                                            .url(user.getUrl())
                                            .content(d.getContent())
                                            .build()
            );
        }
        DetailPlaceDTO detailPlaceDTO = DetailPlaceDTO.builder().place(place).reviewDTOS(placeReviewDTOS).build();

        return new ResponseEntity<>(detailPlaceDTO,HttpStatus.OK);

    }

    @GetMapping(value="/setThumb")
    public void setThumb(){
        PlaceThumb thumb = thumbService.findThumbByNum(0).orElseThrow(()-> new NoSuchDataException("no thumb num : 0"));
        List<Place> placeList= placeService.getAllPlace();
        for (Place p: placeList) {
            if(p.getThumb()== null){
                p.setThumb(thumb);
                placeService.updatePlace(p);
            }
        }
    }


}
