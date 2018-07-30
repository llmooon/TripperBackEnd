package org.soma.tripper.review.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int review_num;

    @Column(name = "user_num")
    private String user_num;

    @Column(name="rating")
    private double rating;

    @Column(name = "content")
    private String content;

    @Column(name="images")
    private int images;

    @Column(name="ml_rating")
    private double ml_rating;

    @Builder Review(String user_num, double rating, String content){
        this.user_num = user_num;
        this.rating = rating;
        this.content = content;
    }

}
