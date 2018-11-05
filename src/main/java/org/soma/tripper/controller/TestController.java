package org.soma.tripper.controller;

import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.place.repository.SeqRepository;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.soma.tripper.schedule.entity.Day;
import org.soma.tripper.schedule.repository.DayRepository;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeqRepository seqRepository;

    @Autowired
    DayRepository dayRepository;

    @GetMapping("/getUserReview/{num}")
    public Review getUserReview(@PathVariable int num){
        return reviewRepository.findReviewByReviewnum(num).orElseThrow(()->new NoSuchDataException());
    }

    @GetMapping("/getUserReview/")
    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    @GetMapping("/getS/{num}")
    public List<Seq> getUserSchedule(@PathVariable int num){
        User user = userRepository.findById(num).orElseThrow(()-> new NoSuchDataException());
        return seqRepository.findSeqsByUser(user).get();
    }
    @GetMapping("/getS")
    public List<Seq> getAllSchedule(){
        //User user = userRepository.findById(num).orElseThrow(()-> new NoSuchDataException());
        return seqRepository.findAll();
    }

    @GetMapping("/getD")
    public List<Day> getAllDay(){
        return dayRepository.findAll();
    }
}
