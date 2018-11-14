package org.soma.tripper.place.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceWithDistance {
    int place_num;
    String name;
    String thumbnum;
    String city;
    double distance;

    @Builder
    public PlaceWithDistance(int place_num, String name, String thumbnum, String city, double distance) {
        this.place_num = place_num;
        this.name = name;
        this.thumbnum = thumbnum;
        this.city = city;
        this.distance = distance;
    }
}
