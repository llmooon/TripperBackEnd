package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review uploadReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<List<Review>> loadReviewByUser(int user_num) {
        return reviewRepository.findReviewByUsernum(user_num);
    }

    @Override
    public Page<Review> loadMainReviewByPage(Pageable page) {
        return reviewRepository.findAll(page);
    }

    @Override
    public Optional<Review> loadReviewByUserAndSeq(int usernum, int seqnum) {
        return reviewRepository.findReviewByUsernumAndSeqnum(usernum,seqnum);
    }

    @Override
    public Optional<Review> loadReviewById(int num) {
        return reviewRepository.findById(num);
    }
//
//    @Override
//    public Review uploadReview(Review review) {
//        return reviewRepository.save(review);
//    }
//
//    @Override
//    public List<Review> loadMainReview() {
//        return reviewRepository.findAll();
//    }
//
//    @Override
//    public Optional<Review> loadReviewByUsernumAndScheduleNum(int usernum,int schedulenum) {
//        return reviewRepository.findReviewByUsernumAndSchedulenum(usernum,schedulenum);
//    }
}






