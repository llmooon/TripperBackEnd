package org.soma.tripper.hotel.controller;

import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.schedule.dto.BasicPlaceDTO;
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
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    PlaceService placeService;

    @GetMapping("/getBusan/{pagenum}")
    public ResponseEntity<List<BasicPlaceDTO>> getBusanHotel(@PathVariable int pagenum){
        PageRequest request = PageRequest.of(pagenum,20);
        Page<Place> res = placeService.getHotel(5,request);
        List<BasicPlaceDTO> basicPlaceDTOS= new ArrayList<>();
        for(Place p : res){
            basicPlaceDTOS.add(BasicPlaceDTO.builder()
                    .city(p.getCity())
                    .name(p.getName())
                    .picture(p.getThumb().getBucket())
                    .placenum(p.getPlace_num())
                    .build());
        }
        return new ResponseEntity<>(basicPlaceDTOS, HttpStatus.OK);
    }
}
