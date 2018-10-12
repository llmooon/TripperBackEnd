package org.soma.tripper.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.place.entity.Place;

@Getter
@ToString
public class PlaceReviewDTO {
    private int reviewnum;
    private String url;
    private String name;
    private String content;

    @Builder
    public PlaceReviewDTO(int reviewnum,String url, String name, String content){
        this.reviewnum=reviewnum;
        this.url=url;
        this.name=name;
        this.content=content;
    }
}
