package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.PlaceThumbService;
import org.soma.tripper.place.dto.DetailPlaceDTO;
import org.soma.tripper.place.dto.PlaceReviewDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.PlacePhoto;
import org.soma.tripper.place.entity.PlaceThumb;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.service.DetailsService;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
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

    @Autowired
    PlaceThumbService placeThumbService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/detail/{num}/{page}")
    @ApiOperation(value = "view place detail",notes = "page는 리뷰 정보")
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
                                            .reviewnum(d.getReviewnum())
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

    @GetMapping(value="/makeImg")
    @ApiOperation(notes = "Don't touch!",value = "don't touch")
    public void makeImg() throws Exception {
        String clientId = "4WWZY0LHZ1bDkTYp6oqk";
        String clientSecret = "BAC0kFG8Cz";
        String url = "https://openapi.naver.com/v1/search/image?query=";
        String next=" &display=5&start=1&sort=sim";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Naver-Client-Id", clientId);
        httpHeaders.add("X-Naver-Client-Secret", clientSecret);

        PlaceThumb thumb=placeThumbService.findThumbByNum(0).orElseThrow(()->new NoSuchDataException("erroer place thumb num"));


        JSONParser jsonParser = new JSONParser();
        List<Place> places = placeService.findPlaceByThumb(thumb);
        List<Place> updateplaces = new ArrayList<>();
        for (Place p: places) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url+p.getName()+next, HttpMethod.GET,new HttpEntity(httpHeaders),String.class);
            String result = responseEntity.getBody();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String link = (String)obj.get("link");
                if(i==0) {
                    PlaceThumb placeThumb = placeThumbService.save(PlaceThumb.builder().bucket(link).build());
                    p.setThumb(placeThumb);
                }

                p.addPhoto(PlacePhoto.builder().bucket(link).build());
            }
           updateplaces.add(p);
        }
        placeService.updatePlaceList(updateplaces);

    }
}
