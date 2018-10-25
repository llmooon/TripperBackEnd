package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review uploadReview(Review review);
    Optional<List<Review>> loadReviewByUser(int user_num);
    Page<Review> loadMainReviewByPage(Pageable page);
    Optional<Review> loadReviewByUserAndSeq(int usernum, int seqnum);
    Optional<Review> loadReviewById(int num);
    Page<Review> loadReviewByStr(String str,Pageable pageable);

}


