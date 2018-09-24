package org.soma.tripper.review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.http.HttpHeaders;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class ReviewDTO {
    private String userEmail;
    @JsonIgnore
    private int usernum;
    private int schedulenum;
    private String content;
    private double rating;
    private List<String> photolist;
    private LocalDateTime createTime;

    @JsonIgnore
    private int placenum;

    public ReviewDTO() {
        super();
    }

    void addphoto(String p){
        if(photolist==null){
            photolist=new ArrayList<>();
        }
        photolist.add(p);
    }

    @Builder ReviewDTO(String userEmail, int usernum, int schedulenum, String content, double rating, LocalDateTime createTime,int placenum){
        this.userEmail=userEmail;
        this.usernum=usernum;
        this.schedulenum=schedulenum;
        this.content=content;
        this.rating=rating;
        this.createTime=createTime;
        this.placenum=placenum;
    }

    public Review toReviewEntity(){
        return Review.builder()
                .usernum(usernum)
                .schedulenum(schedulenum)
                .content(content)
                .rating(rating)
                .placenum(placenum)
                .build();
    }

    public void setPhotolist(List<String> photolist) {
        this.photolist = photolist;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
