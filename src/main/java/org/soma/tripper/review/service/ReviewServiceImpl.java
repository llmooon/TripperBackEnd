package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> loadReview(int user_num, int schedule_num) {
        return reviewRepository.findReviewByUsernumAndSchedulenum(user_num,schedule_num);
    }
}






