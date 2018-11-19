package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Review> loadMainReviewByPage(Pageable pageable,int isvalid) {
        return reviewRepository.findReviewsByIsvalid(pageable,isvalid);
    }

    @Override
    public Optional<Review> loadReviewByUserAndSeq(int usernum, int seqnum) {
        return reviewRepository.findReviewByUsernumAndSeqnum(usernum,seqnum);
    }

    @Override
    public Optional<Review> loadReviewById(int num) {
        return reviewRepository.findReviewByReviewnum(num);
    }

    @Override
    public Page<Review> loadReviewByStr(String str,Pageable pageable) {
        return reviewRepository.findReviewBystr(str,pageable);
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Optional<Review> loadReviewBySeqnum(int seqnum) {
        return reviewRepository.findReviewByReviewnum(seqnum);
    }

}






