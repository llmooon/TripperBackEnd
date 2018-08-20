package org.soma.tripper.place.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.schedule.entity.Schedule;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeqDTO {
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private int usernum;
    private List<Schedule> schedulelist;

    @Builder
    public SeqDTO(int seqnum,Date fromdate,Date toDate,int usernum,List<Schedule> schedulelist){
        this.seqnum=seqnum;
        this.fromdate=fromdate;
        this.toDate=toDate;
        this.usernum=usernum;
        this.schedulelist=schedulelist;
    }
}


