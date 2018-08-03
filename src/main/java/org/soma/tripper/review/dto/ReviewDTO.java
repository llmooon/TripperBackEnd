package org.soma.tripper.review.dto;

import lombok.Builder;
import org.soma.tripper.review.entity.Review;

import java.io.File;
import java.util.List;

public class ReviewDTO {
    int usernum;
    int schedulenum;
    String content;
    double rating;
    List<File> photolist;

    public ReviewDTO() {
        super();
    }

    @Builder ReviewDTO(int usernum, int schedulenum, String content, double rating, List<File> photolist){
        this.usernum=usernum;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
        this.photolist=photolist;
    }

    public Review toEntity(int usernum, int schedulenum, String content,double rating){
        return Review.builder()
                .user_num(usernum)
                .schedulenum(schedulenum)
                .content(content)
                .rating(rating)
                .build();
    }



}
