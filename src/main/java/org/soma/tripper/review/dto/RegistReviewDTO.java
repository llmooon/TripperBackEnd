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

        public RegistReviewDTO() {
            super();
        }

//        public ReviewDTO toReviewDTO(){
//            return ReviewDTO.builder()
//                    .usernum(usernum)
//                    .schedulenum(schedulenum)
//                    .content(content)
//                    .rating(rating)
//                    //.photolist(photolist)
//                    .build();
//        }

}
