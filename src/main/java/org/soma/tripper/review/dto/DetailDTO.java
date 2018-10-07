package org.soma.tripper.review.dto;

import lombok.*;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.schedule.entity.Schedule;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailDTO {
    private int schedulenum;
    private String content;
    private List<String> photos;

    @Builder DetailDTO(int schedulenum,String content,List<String> photos){
        this.schedulenum=schedulenum;
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


