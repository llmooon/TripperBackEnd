package org.soma.tripper.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.schedule.entity.Schedule;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailDTO {
    private int detailsnum;
    private String content;
    private List<String> photos;
    @JsonIgnore Schedule schedule;

    @Builder DetailDTO(int detailsnum, String content, List<String> photos,Schedule schedule){
        this.detailsnum = detailsnum;
        this.content=content;
        this.photos=photos;
        this.schedule=schedule;
    }

    public Details toEntity(Schedule schedule){
        return Details.builder()
                .content(content)
                .schedule(schedule)
                .build();
    }
}


