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
    private String encity;
    private String krcity;

    @Builder
    public Airport(String English, String korea, String code){
        this.encity =English;
        this.krcity =korea;
        this.code=code;
    }
}

