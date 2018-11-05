package org.soma.tripper.schedule.dto;

import lombok.*;
import org.soma.tripper.schedule.entity.Day;

import java.sql.Date;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyScheduleDTO {
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private String title;
    private String region;


    @Builder
    public MyScheduleDTO(int seqnum,Date fromdate,String title,Date toDate,String region){
        this.seqnum=seqnum;
        this.fromdate=fromdate;
        this.title=title;
        this.toDate=toDate;
        this.region=region;
    }
}
