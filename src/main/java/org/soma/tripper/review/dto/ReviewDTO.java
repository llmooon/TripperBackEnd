package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.http.HttpHeaders;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class ReviewDTO {
    private int usernum;
    private int schedulenum;
    private String content;
    private double rating;
    private List<String> photolist;

    public ReviewDTO() {
        super();
    }

    void addphoto(String p){
        if(photolist==null){
            photolist=new ArrayList<>();
        }
        photolist.add(p);
    }

    @Builder ReviewDTO(int usernum, int schedulenum, String content, double rating){
        this.usernum=usernum;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
    }

    public Review toReviewEntity(){
        return Review.builder()
                .usernum(usernum)
                .schedulenum(schedulenum)
                .content(content)
                .rating(rating)
                .build();
    }

    public void setPhotolist(List<String> photolist) {
        this.photolist = photolist;
    }
}
