package org.soma.tripper.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SimplePlaceDTO {
    private String name;
    private String location;
    private String thumb;

    @Builder
    public SimplePlaceDTO(String name, String location, String thumb){
        this.name=name;
        this.location=location;
        this.thumb=thumb;
    }
}
