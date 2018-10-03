package org.soma.tripper.review.dto;

import lombok.*;
import org.soma.tripper.review.entity.Details;

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

    public Details toEntity(){
        return Details.builder()
                .content(content)
                .schedulenum(schedulenum)
                .build();
    }
}


