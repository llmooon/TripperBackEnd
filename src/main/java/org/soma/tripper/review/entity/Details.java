package org.soma.tripper.review.entity;

import lombok.*;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.schedule.entity.Schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Details {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int detailsnum;
    private String content;
    private int reviewnum;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "detailsnum")
    private List<Photo> photos;

    public void setPhoto(List<Photo> photoList){
        this.photos.clear();
        this.photos.addAll(photoList);
    }

    public void removePhoto(Photo photo){
        photos.remove(photo);
    }


    public void addPhoto(Photo p){
        if(photos==null) photos=new ArrayList<>();
        photos.add(p);
    }

    public void deleteContent(){
        this.content=null;
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="schedulenum")
    private Schedule schedule;

    public void setSchedule(Schedule schedule) {
        this.schedule=schedule;
    }

    @Builder
    public Details(Schedule schedule, String content ){
        this.schedule=schedule;
        this.content=content;
    }

}
