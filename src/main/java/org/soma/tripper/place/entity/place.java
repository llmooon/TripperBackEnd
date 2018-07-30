package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String place_num;

    private String country;
    private String city;
    private String name;
    private String details;
    private double latitude;
    private double longtitude;
    private int price;
    private String type;


}
