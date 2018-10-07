package org.soma.tripper.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.place.entity.Place;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class DetailPlaceDTO {
    private Place place;

    private List<PlaceReviewDTO> reviewDTOS;

    @Builder
    public  DetailPlaceDTO(Place place, List<PlaceReviewDTO> reviewDTOS){
        this.place=place;
        this.reviewDTOS=reviewDTOS;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    public void addReview(PlaceReviewDTO placeReviewDTO){
        if(reviewDTOS==null) reviewDTOS=new ArrayList<>();
        reviewDTOS.add(placeReviewDTO);
    }

    public void setReviewDTOS(List<PlaceReviewDTO> reviewDTOS) {
        this.reviewDTOS = reviewDTOS;
    }
}
