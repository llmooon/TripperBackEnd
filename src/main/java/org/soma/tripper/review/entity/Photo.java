package org.soma.tripper.review.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int photonum;

    @Column(name="bucket")
    private String bucket;

    @ManyToOne(targetEntity = Review.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewnum")
    private Collection<Review> review;

    public void addreview(Review r){
        if(review==null){
            review=new ArrayList<>();
        }
        review.add(r);
    }

    @Builder
    Photo(int photonum,String bucket, int reviewnum){
        this.photonum=photonum;
        this.bucket=bucket;

    }
}
