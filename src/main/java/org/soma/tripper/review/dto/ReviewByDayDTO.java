package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ReviewByDayDTO {
    private String user;
    private int seqnum;
    private String thumb;
    private List<DayDTO> days;

    @Builder ReviewByDayDTO(String user, int seqnum,List<DayDTO> days,String thumb){
        this.user=user;
        this.seqnum=seqnum;
        this.days=days;
        this.thumb=thumb;
    }
}
