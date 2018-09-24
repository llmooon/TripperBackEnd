package org.soma.tripper.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.review.dto.ImagePath;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.dto.ReviewDTO;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.repository.ReviewRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ScheduleService scheduleService;

    private AmazonClient amazonClient;

    @Autowired
    ReviewController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }

    String s3Url = "https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/userload/{userEmail}")
    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail){
        int userNum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        List<Review> reviews = reviewService.loadReviewByUser(userNum);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @ApiOperation(value="upload review only content",notes = "사진을 제외한 콘텐츠 업로드")
    @PostMapping(value = "/uploadContent")
    public ResponseEntity<ReviewDTO> uploadReview (@RequestParam String useremail, @RequestParam int schedulenum,
                                                   @RequestParam String content, @RequestParam double rating){

        int usernum=userService.findUserByEmail(useremail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        int placenum=scheduleService.findScheduleById(schedulenum).orElseThrow(()->new NoSuchDataException()).getPlace().getPlace_num();

            ReviewDTO reviewDTO = ReviewDTO.builder()
                    .content(content)
                    .rating(rating)
                    .schedulenum(schedulenum)
                    .usernum(usernum)
                    .placenum(placenum)
                    .build();
            Review review = reviewDTO.toReviewEntity();
            reviewService.uploadReview(review);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="upload review only content",notes = "사진을 제외한 콘텐츠 업로드")
    @PostMapping(value = "/uploadPhoto")
    public ResponseEntity<ReviewDTO> uploadPhoto(@RequestParam String useremail, @RequestParam int schedulenum,
                                                   @RequestParam MultipartFile file){

        int usernum=userService.findUserByEmail(useremail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        Review review = reviewService.loadReviewByUsernumAndScheduleNum(usernum,schedulenum).orElseThrow(()->new NoSuchDataException("해당 리뷰 정보 없음."));
        ImagePath imagePath = this.amazonClient.uploadFile(file);
        if(review.getThumb()==null)
            review.setThumb(Thumb.builder().bucket(s3Url+imagePath.getDateName()+"/thumb/"+imagePath.getFileName()).build());
        review.addPhoto(Photo.builder().bucket(s3Url+imagePath.getDateName()+"/"+imagePath.getFileName()).build());
        reviewService.uploadReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/loadMainReviewByPaging/{page}")
    public ResponseEntity<List<MainReviewDTO>> loadMainReviewByPaging(@PathVariable Integer page) throws IOException {
        int size = 10;
        PageRequest request = PageRequest.of(page, size, new Sort(Sort.Direction.ASC, "reviewnum"));
        Page<Review> result = reviewService.loadMainReviewByPage(request);
        List<Review> reviewList = result.getContent();
        List<MainReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review r : reviewList) {
            MainReviewDTO reviewDTO = r.toMainReviewDTO();
            User user = userService.findUserByUsernum(r.getUsernum()).get();//.orElseThrow(()->new NoSuchDataException("유저 정보가 없습니다."));
            reviewDTO.setUser(user.getEmail());
            Thumb thumb = r.getThumb();
            String photoUrl = thumb.getBucket();
            reviewDTO.setPhotoDTO(photoUrl);
            reviewDTOList.add(reviewDTO);
        }

        return new ResponseEntity<>(reviewDTOList,HttpStatus.OK);
    }
}
