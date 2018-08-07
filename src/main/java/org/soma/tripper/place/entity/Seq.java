package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.user.domain.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seq {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seqnum;

    private int usernum;
    private Date fromdate;
    private Date toDate;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name="user_num")
    private User user;

}
