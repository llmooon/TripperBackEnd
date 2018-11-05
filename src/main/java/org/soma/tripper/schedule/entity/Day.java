package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.omg.CORBA.portable.IDLEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@JsonIgnore
    private int daynum;

    private int day;
    @JsonIgnore
    private int seqnum;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "daynum")
    private List<Schedule> schedulelist;

    @Builder
    public Day(int day,int seqnum, List<Schedule> schedulelist){
        this.day=day;
        this.seqnum=seqnum;
        this.schedulelist=schedulelist;
    }

}
