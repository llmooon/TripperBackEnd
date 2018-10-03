package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.soma.tripper.place.entity.Place;

import javax.persistence.*;
import java.sql.Date;

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

    Date startTime;

    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "placenum")
    private Place place;

    @Builder
    public Schedule(int daynum,Place place){
        this.daynum=daynum;
        this.place=place;
    }
}
