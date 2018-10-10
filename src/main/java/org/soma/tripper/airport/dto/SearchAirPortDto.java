package org.soma.tripper.airport.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class SearchAirPortDto {
    String airline;
    String arrivalcity;
    String domesticArrivalTime;
    String domesticNum;
    String domesticStartTime;
    String startcity;
}
