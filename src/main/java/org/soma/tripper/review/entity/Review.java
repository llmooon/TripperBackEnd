package org.soma.tripper.review.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.soma.tripper.common.BaseTimeEntity;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.review.dto.DetailDTO;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.dto.ReviewDTO;

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
    private int isvalid;

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="thumbnum")
    private Thumb thumb;


    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "reviewnum")
    @OrderBy("detailsnum")
    private List<Details> details;

    public void setDetails(List<Details> details) {
        this.details.clear();
        this.details.addAll(details);
    }

    public void removeDetails(Details d){
        details.remove(d);
    }
    public void adddetails(Details detail){
        if(details==null) details=new ArrayList<>();
        details.add(detail);
    }

    public ReviewDTO toReviewDTO(){
        List<DetailDTO> detailDTOS = toDetailDTO();
        return ReviewDTO.builder()
                .reviewnum(this.reviewnum)
                .reviews(detailDTOS)
                .seqnum(this.seqnum)
               // .thumb(this.thumb)
                .build();
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
                    .schedule(d.getSchedule())
                    .photos(photos)
                    .detailsnum(d.getDetailsnum())
                    .build());
        }
        return detailDTOS;
    }

    @Builder Review(int usernum, int seqnum,int isvalid){
        this.usernum=usernum;
        this.seqnum=seqnum;
        this.isvalid=isvalid;
    }

    public void setView(int view) {
        this.view = view;
    }
}
