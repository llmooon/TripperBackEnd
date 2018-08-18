package org.soma.tripper.schedule.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;
import org.soma.tripper.place.entity.Place;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int schedulenum;

    int seqnum;
    int usernum;
    int day;
    Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placenum")
    private Place place;

}
