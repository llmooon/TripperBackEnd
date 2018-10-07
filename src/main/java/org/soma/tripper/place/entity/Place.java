package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Thumb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
    private double rating;
    @Lob
    @Column(columnDefinition = "text")
    private String details;
    private double latitude;
    private double longtitude;
    private int price;
    private String type;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="thumbnum")
    private PlaceThumb thumb;

    public void setThumb(PlaceThumb thumb) {
        this.thumb = thumb;
    }

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "detailsnum")
    private Collection<PlacePhoto> photos;

    public void addPhoto(PlacePhoto p){
        if(photos==null) photos=new ArrayList<>();
        photos.add(p);
    }


}
