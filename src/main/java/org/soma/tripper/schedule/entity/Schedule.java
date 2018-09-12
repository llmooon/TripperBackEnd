package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.place.entity.Place;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int schedulenum;
    //int seqnum;
    int day;
    Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placenum")
    private Place place;

//    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinColumn(name = "schedulenum")
//    private List<Schedule> schedulelist;


    @Builder
    public Schedule(int schedulenum,int day,Place place){
        this.schedulenum=schedulenum;
        this.day=day;
        this.place=place;
    }

}
