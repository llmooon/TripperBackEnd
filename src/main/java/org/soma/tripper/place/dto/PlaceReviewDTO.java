package org.soma.tripper.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.place.entity.Place;

@Getter
@ToString
public class PlaceReviewDTO {
    private String url;
    private String name;
    private String content;

    @Builder
    public PlaceReviewDTO(String url, String name, String content){
        this.url=url;
        this.name=name;
        this.content=content;
    }
}
