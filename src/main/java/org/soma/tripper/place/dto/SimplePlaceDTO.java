package org.soma.tripper.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SimplePlaceDTO {
    private int placenum;
    private String name;
    private String location;
    private String thumb;

    @Builder
    public SimplePlaceDTO(int placenum,String name, String location, String thumb){
        this.placenum=placenum;
        this.name=name;
        this.location=location;
        this.thumb=thumb;
    }
}
