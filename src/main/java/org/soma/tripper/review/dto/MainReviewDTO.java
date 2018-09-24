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
    private String user;
    private int schedulenum;
    private String content;
    private double rating;
    private String photoDTO;

    public void setPhotoDTO(String photoDTO) {
        this.photoDTO = photoDTO;
    }

    public void setUser(String user) {this.user = user; }

    @Builder
    MainReviewDTO(String user, int schedulenum, String content, double rating){
        this.user=user;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
    }


}
