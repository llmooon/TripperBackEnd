package org.soma.tripper.review.entity;

import lombok.*;
import org.soma.tripper.schedule.entity.Schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Details {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int detailsnum;

    private int schedulenum;
    private String content;
    private int reviewnum;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "detailsnum")
    private Collection<Photo> photos;

    public void addPhoto(Photo p){
        if(photos==null) photos=new ArrayList<>();
        photos.add(p);
    }

    @Builder
    public Details(int schedulenum, String content ){
        this.schedulenum=schedulenum;
        this.content=content;
    }
}
