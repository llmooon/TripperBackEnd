package org.soma.tripper.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.omg.CORBA.portable.IDLEntity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "daynum")
    @OrderBy("startTime asc")
    private List<Schedule> schedulelist;


    public void setSchedulelist(List<Schedule> schedulelist) {
        this.schedulelist = schedulelist;
    }

    public void deleteScheduleList(){
        schedulelist=null;
    }

    public void addScheduleList(Schedule schedule){
        if(schedulelist==null) schedulelist = new ArrayList<>();
        schedulelist.add(schedule);
    }

    public void removeChild(Schedule schedule){
        this.schedulelist.remove(schedule);
    }
    @Builder
    public Day(int day,int seqnum, List<Schedule> schedulelist){
        this.day=day;
        this.seqnum=seqnum;
        this.schedulelist=schedulelist;
    }

}
