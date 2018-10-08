package org.soma.tripper.airport.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airport {
    @Id
    private String code;
    private String ENcity;
    private String KRcity;

    @Builder
    public Airport(String English, String korea, String code){
        this.ENcity=English;
        this.KRcity=korea;
        this.code=code;
    }
}

