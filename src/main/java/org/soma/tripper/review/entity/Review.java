package org.soma.tripper.review.entity;

import lombok.*;
import org.soma.tripper.practice.Phone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
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
        this.usernum = schedulenum;
        this.content=content;
        this.rating = rating;
    }

}
