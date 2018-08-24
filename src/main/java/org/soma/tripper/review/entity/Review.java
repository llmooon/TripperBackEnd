package org.soma.tripper.review.entity;

import lombok.*;
import org.soma.tripper.common.BaseTimeEntity;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.dto.ReviewDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int reviewnum;

    @Column(name = "usernum")
    private int usernum;

    @Column(name = "schedule_num")
    private int schedulenum;

    @Column(name = "content",length = 1000)
    private String content;

    @Column(name="rating")
    private double rating;

    @Column(name="ml_rating")
    private double mlrating;

    @Column(name="view")
    private int view;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="thumbnum")
    private Thumb thumb;


    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewnum")
    private Collection<Photo> photos;


    public void addPhoto(Photo p){
        if(photos==null){
            photos=new ArrayList<>();
        }
        photos.add(p);
    }

    @Builder Review(int usernum, int schedulenum, String content, double rating){
        this.usernum=usernum;
        this.schedulenum = schedulenum;
        this.content=content;
        this.rating = rating;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public ReviewDTO toReviewDTO(){
        return ReviewDTO.builder()
                .content(content)
                .rating(rating)
                .schedulenum(schedulenum)
                .usernum(usernum)
                .createTime(getCreatedDate())
                .build();
    }
    public MainReviewDTO toMainReviewDTO(){
        return MainReviewDTO.builder()
                .content(content)
                .rating(rating)
                .schedulenum(schedulenum)
                .usernum(usernum)
                .build();
    }

}
