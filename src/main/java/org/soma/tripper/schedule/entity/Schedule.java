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
    int day;
    int seqnum;
    Date startTime;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "placenum")
    private Place place;

    @Builder
    public Schedule(int schedulenum,int day,Place place){
        this.schedulenum=schedulenum;
        this.day=day;
        this.place=place;
    }
}
