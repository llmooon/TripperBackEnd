package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.soma.tripper.place.entity.Place;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int schedulenum;

    @JsonIgnore
    int daynum;

    LocalDateTime startTime;

    @ManyToOne(targetEntity = Place.class,fetch = FetchType.EAGER)//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "placenum")
    private Place place;

    @Builder
    public Schedule(int daynum,Place place,LocalDateTime startTime){
        this.daynum=daynum;
        this.place=place;
        this.startTime=startTime;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
