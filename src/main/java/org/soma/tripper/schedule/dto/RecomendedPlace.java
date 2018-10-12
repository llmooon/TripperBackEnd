package org.soma.tripper.schedule.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecomendedPlace {
    int placenum;
    String name;
    String picture;
    String city;

    @Builder
    public RecomendedPlace(int placenum,String name,String picture,String city){
        this.placenum=placenum;
        this.name=name;
        this.picture=picture;
        this.city=city;
    }
}
