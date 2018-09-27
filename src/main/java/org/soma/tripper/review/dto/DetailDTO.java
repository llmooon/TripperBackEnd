package org.soma.tripper.review.dto;

import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.review.entity.Details;

import java.util.List;

@Getter
@ToString
public class DetailDTO {
    private int schedulenum;
    private String content;

    public Details toEntity(){
        return Details.builder()
                .content(content)
                .schedulenum(schedulenum)
                .build();
    }
}


