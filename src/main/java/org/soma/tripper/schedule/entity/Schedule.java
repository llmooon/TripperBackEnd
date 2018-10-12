package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.soma.tripper.place.entity.Place;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "placenum")
    private Place place;

    @Builder
    public Schedule(int daynum,Place place,LocalDateTime startTime){
        this.daynum=daynum;
        this.place=place;
        this.startTime=startTime;
    }
}
