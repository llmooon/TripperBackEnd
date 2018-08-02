package org.soma.tripper.review.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
@Entity

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int review_num;

    @Column(name = "user_num")
    private int usernum;

    @Column(name = "schedule_num")
    private int schedulenum;

    @Column(name = "content",length = 1000)
    private String content;

    @Column(name="rating")
    private double rating;

    @Column(name="ml_rating")
    private double mlrating;

    @Builder Review(int user_num, int schedulenum, String content, double rating){
        this.usernum=user_num;
        this.usernum = schedulenum;
        this.content=content;
        this.rating = rating;
    }

}
