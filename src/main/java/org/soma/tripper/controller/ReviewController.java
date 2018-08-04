package org.soma.tripper.controller;

import org.soma.tripper.review.dto.RegistReviewDTO;
import org.soma.tripper.review.dto.ReviewDTO;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.repository.ReviewRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;


    private AmazonClient amazonClient;
    @Autowired
    ReviewController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }


    @GetMapping("/userload/{userEmail}/{schedule_num}")
    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail, @PathVariable int schedule_num){
        int userNum = userRepository.findByEmail(userEmail).getUser_num(); // 나중에 고치기,,,
        List<Review> reviews = reviewService.loadReview(userNum,schedule_num);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReviewDTO> uploadReview (@RequestPart RegistReviewDTO registReviewDTO, @RequestPart MultipartFile file){


        //@RequestBody RegistReviewDTO registReviewDTO, @RequestBody MultipartFile file
        //System.out.println(file);
       // String dir = this.amazonClient.uploadFile(file);
        String dir = "!";
        ReviewDTO reviewDTO = registReviewDTO.toReviewDTO();
        Review review = reviewDTO.toReviewEntity();
        review.addPhoto(Photo.builder().bucket(dir).build());
        Review result = reviewService.uploadReview(review);

        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PostMapping("/uploadMultiFiles ")
//    public ResponseEntity<ReviewDTO> testupload (@RequestPart(value="files")MultipartFile[] files){
//
//        for (MultipartFile i:files) {
//            this.amazonClient.uploadFile(i);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
