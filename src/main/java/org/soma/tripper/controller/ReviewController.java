package org.soma.tripper.controller;

import org.soma.tripper.review.dto.ReviewDTO;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    PhotoRepository photoRepository;


    private AmazonClient amazonClient;
    @Autowired
    ReviewController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }


    @GetMapping("/userload/{userEmail}/{schedule_num}")
    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail, @PathVariable int schedule_num){
        int userNum = userService.findUserByEmail(userEmail).getUser_num(); // 나중에 고치기,,,
        List<Review> reviews = reviewService.loadReview(userNum,schedule_num);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @PostMapping(value = "/upload")
    public ResponseEntity<ReviewDTO> uploadReview (@RequestParam String useremail, @RequestParam int schedulenum,
                                                   @RequestParam String content, @RequestParam double rating, @RequestParam  MultipartFile file){

        User user=userService.findUserByEmail(useremail);
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int usernum = user.getUser_num();

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .content(content)
                .rating(rating)
                .schedulenum(schedulenum)
                .usernum(usernum)
                .build();


        String dir = this.amazonClient.uploadFile(file);
        Review review = reviewDTO.toReviewEntity();
        review.addPhoto(Photo.builder().bucket(dir).build());
        Review result = reviewService.uploadReview(review);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value="/loadMainReview")
    public ResponseEntity<List<ReviewDTO>> loadMainReview() throws IOException {
        List<Review> reviewList = reviewService.loadMainReview();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review r: reviewList) {
            Collection<Photo> photoList = r.getPhotos();
            List<byte[]> photos = new ArrayList<>();

            ReviewDTO reviewDTO = r.toReviewDTO();
            for (Photo p: photoList) {
                photos.add(amazonClient.download(p.getBucket()));
            }
            reviewDTO.setPhotolist(photos);
            reviewDTOList.add(reviewDTO);
        }
        return new ResponseEntity<>(reviewDTOList,HttpStatus.OK);
    }

//    @GetMapping("/downloadFile")
//    public ResponseEntity<byte[]> download() throws IOException {
//        //return amazonClient.download();
//    }
}
