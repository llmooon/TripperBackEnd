package org.soma.tripper.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ModifySeqDTO {
    private int seqnum;
    private Date fromdate;
    private Date toDate;
    private String title;
    private String user;
    private List<ModifyDayDTO> dayList;

}
