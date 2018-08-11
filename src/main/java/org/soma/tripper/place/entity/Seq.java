package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.user.domain.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seqnum;
    private Date fromdate;
    private Date toDate;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name="usernum")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "seqnum")
    private Collection<Schedule> schedulelist;

}
