package org.soma.tripper.place.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.repository.UserRepository;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeqDTO {
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private String user;
    private List<Schedule> schedulelist;

    @Builder
    public SeqDTO(int seqnum,Date fromdate,Date toDate,String user,List<Schedule> schedulelist){
        this.seqnum=seqnum;
        this.fromdate=fromdate;
        this.toDate=toDate;
        this.user=user;
        this.schedulelist=schedulelist;
    }
}


