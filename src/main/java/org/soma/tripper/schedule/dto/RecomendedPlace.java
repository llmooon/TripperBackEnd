package org.soma.tripper.schedule.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecomendedPlace {
    String name;
    String picture;
    String city;

    @Builder
    public RecomendedPlace(String name,String picture,String city){
        this.name=name;
        this.picture=picture;
        this.city=city;
    }
}
