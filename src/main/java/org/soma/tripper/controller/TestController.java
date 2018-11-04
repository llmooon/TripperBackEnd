package org.soma.tripper.controller;

import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/getUserReview/{num}")
    public Review getUserReview(@PathVariable int num){
        return reviewRepository.findById(num).orElseThrow(()->new NoSuchDataException());
    }
}
