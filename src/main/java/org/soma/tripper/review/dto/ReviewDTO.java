package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
public class ReviewDTO {
    private int usernum;
    private int schedulenum;
    private String content;
    private double rating;
    //private List<MultipartFile> photolist;

    public ReviewDTO() {
        super();
    }

    @Builder ReviewDTO(int usernum, int schedulenum, String content, double rating){
        this.usernum=usernum;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
        //this.photolist=photolist;
    }

    public Review toReviewEntity(){
        return Review.builder()
                .usernum(usernum)
                .schedulenum(schedulenum)
                .content(content)
                .rating(rating)
                .build();
    }

}
