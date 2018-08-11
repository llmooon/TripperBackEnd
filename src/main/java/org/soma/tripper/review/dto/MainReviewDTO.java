package org.soma.tripper.review.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soma.tripper.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MainReviewDTO {
    private int usernum;
    private int schedulenum;
    private String content;
    private double rating;
    private String photoDTO;

    public void setPhotoDTO(String photoDTO) {
        this.photoDTO = photoDTO;
    }

    @Builder
    MainReviewDTO(int usernum, int schedulenum, String content, double rating){
        this.usernum=usernum;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
    }


}
