package org.soma.tripper.place.entity;

import lombok.*;
import org.soma.tripper.place.dto.SeqDTO;
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

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="usernum")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "seqnum")
    private List<Schedule> schedulelist;


    @Builder
    public Seq(int seqnum,User user,List<Schedule> schedulelist,Date fromdate, Date toDate){
        this.seqnum=seqnum;
        this.user = user;
        this.schedulelist=schedulelist;
        this.fromdate=fromdate;
        this.toDate=toDate;
    }

    public void setSchedulelist(List<Schedule> schedulelist) {
        this.schedulelist = schedulelist;
    }

    public SeqDTO toDTO(){
        return SeqDTO.builder()
                .seqnum(this.getSeqnum())
                .fromdate(this.getFromdate())
                .toDate(this.getToDate())
                .user(this.user.getEmail())
                .schedulelist(this.getSchedulelist())
                .build();
    }
}
