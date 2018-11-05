package org.soma.tripper.review.dto;

import lombok.*;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDTO {
    private int reviewnum;
    private int seqnum;
    private List<DetailDTO> reviews;
    private Thumb thumb;

    @Builder
    public ReviewDTO(int reviewnum, int seqnum, List<DetailDTO> reviews, Thumb thumb) {
        this.reviewnum = reviewnum;
        this.seqnum = seqnum;
        this.reviews = reviews;
        this.thumb = thumb;
    }
}
