package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.soma.tripper.review.entity.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class RegistReviewDTO {
        private int usernum;
        private int schedulenum;
        private String content;
        private double rating;
   //     private List<MultipartFile> photolist;

        public RegistReviewDTO() {
            super();
        }

//        @Builder
//        RegistReviewDTO(int usernum, int schedulenum, String content, double rating, List<MultipartFile> photolist){
//            this.usernum=usernum;
//            this.schedulenum=schedulenum;
//            this.content=content;
//            this.rating=rating;
//            this.photolist=photolist;
//        }

        public ReviewDTO toReviewDTO(){
            return ReviewDTO.builder()
                    .usernum(usernum)
                    .schedulenum(schedulenum)
                    .content(content)
                    .rating(rating)
                    //.photolist(photolist)
                    .build();
        }

}
