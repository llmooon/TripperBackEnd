package org.soma.tripper.controller;

import org.soma.tripper.review.dto.ReviewDTO;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.ReviewRepository;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/userload/{userEmail}/{schedule_num}")
    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail, @PathVariable int schedule_num){
        int userNum = userRepository.findByEmail(userEmail).getUser_num();
        List<Review> reviews = reviewService.loadReview(userNum,schedule_num);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity<ReviewDTO> upload(@RequestBody ReviewDTO reviewDTO){


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
