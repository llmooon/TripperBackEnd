package org.soma.tripper.review.entity;

import lombok.*;
import org.soma.tripper.common.BaseTimeEntity;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.review.dto.DetailDTO;
import org.soma.tripper.review.dto.MainReviewDTO;

import javax.persistence.*;
import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int reviewnum;

    private int usernum;
    private int seqnum;
    private double mlrating;
    private int view;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="thumbnum")
    private Thumb thumb;


    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewnum")
    private Collection<Details> details;

    public void adddetails(Details detail){
        if(details==null) details=new ArrayList<>();
        details.add(detail);
    }

    public List<DetailDTO> toDetailDTO(){
        List<DetailDTO> detailDTOS = new ArrayList<>();
        for(Details d : details){
            List<String> photos = new ArrayList<>();
            for(Photo p:d.getPhotos()){
                photos.add(p.getBucket());
            }
            detailDTOS.add(
                    DetailDTO.builder()
                    .content(d.getContent())
                    .photos(photos)
                    .schedulenum(d.getSchedule().getSchedulenum())
                    .build());
        }
        return detailDTOS;
    }

    @Builder Review(int usernum, int seqnum){
        this.usernum=usernum;
        this.seqnum=seqnum;
    }

}
