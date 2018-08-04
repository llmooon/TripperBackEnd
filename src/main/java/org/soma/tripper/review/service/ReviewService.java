package org.soma.tripper.review.service;

import org.soma.tripper.practice.Member;
import org.soma.tripper.review.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> loadReview(int user_num, int schedule_num);
    Review uploadReview(Review review);
}


