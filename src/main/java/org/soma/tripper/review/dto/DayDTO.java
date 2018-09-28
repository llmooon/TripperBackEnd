package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class DayDTO {
    int day;
    List<DetailDTO> detailDTOS;

    public void addDetails(DetailDTO d){
        if(detailDTOS==null) detailDTOS=new ArrayList<>();
        detailDTOS.add(d);
    }

    @Builder DayDTO(int day, List<DetailDTO> details){
        this.day=day;
        this.detailDTOS=details;
    }
}
