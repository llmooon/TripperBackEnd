package org.soma.tripper.review.dto;

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

    @Builder DetailDTO(int detailsnum, String content, List<String> photos){
        this.detailsnum = detailsnum;
        this.content=content;
        this.photos=photos;
    }

    public Details toEntity(Schedule schedule){
        return Details.builder()
                .content(content)
                .schedule(schedule)
                .build();
    }
}


