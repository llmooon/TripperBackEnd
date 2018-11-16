package org.soma.tripper.place.dto;

import lombok.*;
import org.soma.tripper.schedule.entity.Day;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SeqDTO {
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private String title;
    private String user;
    private List<Day> dayList;

    @Builder
    public SeqDTO(int seqnum,Date fromdate,String title,Date toDate,String user, List<Day> dayList){
        this.seqnum=seqnum;
        this.fromdate=fromdate;
        this.title=title;
        this.toDate=toDate;
        this.user=user;
        this.dayList=dayList;
    }

}


