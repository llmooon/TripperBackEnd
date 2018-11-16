package org.soma.tripper.place.entity;

import lombok.*;
import org.soma.tripper.place.dto.SeqDTO;
import org.soma.tripper.schedule.dto.MyScheduleDTO;
import org.soma.tripper.schedule.entity.Day;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.user.domain.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Seq {

    @Id
    @Column(name = "seqnum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private String title;
    private int totalday;
    private String region;

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="usernum")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "seqnum")
    @OrderBy("daynum asc")
    private List<Day> dayList;

    public void removeDay(Day day){
        this.dayList.remove(day);
    }

    @Builder
    public Seq(int seqnum,User user,List<Day> dayList,Date fromdate,String region, Date toDate,int totalday){
        this.seqnum=seqnum;
        this.user = user;
        this.dayList=dayList;
        this.fromdate=fromdate;
        this.toDate=toDate;
        this.region=region;
        this.totalday=totalday;
    }

    public void setSchedulelist(List<Day> dayList) {
        this.dayList.clear();
        this.dayList.addAll(dayList);

    }

    public SeqDTO toDTO(){
        return SeqDTO.builder()
                .seqnum(this.getSeqnum())
                .fromdate(this.getFromdate())
                .toDate(this.getToDate())
                .title(this.getTitle())
                .user(this.user.getEmail())
                .dayList(this.getDayList())
                .build();
    }
    public MyScheduleDTO toMyDTO(){
        return MyScheduleDTO.builder()
                .fromdate(fromdate)
                .title(title)
                .toDate(toDate)
                .seqnum(seqnum)
                .region(region)
                .build();
    }
}
