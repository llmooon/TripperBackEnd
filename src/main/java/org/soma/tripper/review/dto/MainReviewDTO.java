package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@ToString
public class MainReviewDTO {
    private int reviewnum;
    private String title;
    private String writer;
    private LocalDateTime time;
    private String photo;

    @Builder
    public MainReviewDTO(int reviewnum,String title, String writer, LocalDateTime time, String photo){
        this.reviewnum=reviewnum;
        this.title=title;
        this.writer=writer;
        this.time=time;
        this.photo=photo;
    }
}
