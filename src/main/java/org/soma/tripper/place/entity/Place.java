package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int place_num;

    private String country;
    private String city;
    private String name;

    @Lob
    @Column(columnDefinition = "text")
    private String details;
    private double latitude;
    private double longtitude;
    private int price;
    private String type;
}
