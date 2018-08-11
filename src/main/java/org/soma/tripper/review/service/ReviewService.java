package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    List<Review> loadReview(int user_num, int schedule_num);
    Review uploadReview(Review review);
    List<Review> loadMainReview();
    Page<Review> loadMainReviewByPage(Pageable page);

}


