package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.schedule.entity.Schedule;

import java.util.List;

@Getter
@ToString
public class ReadDetailDTO {

    private Schedule schedule;
    private String content;
    private List<String> photos;

    @Builder
    ReadDetailDTO(Schedule schedule, String content, List<String> photos){
        this.schedule=schedule;
        this.content=content;
        this.photos=photos;
    }
}
