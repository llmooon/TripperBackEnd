package org.soma.tripper.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.review.dto.DetailDTO;
import org.soma.tripper.review.dto.ImagePath;
import org.soma.tripper.review.dto.ReviewDTO;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.DetailsService;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    DetailsService detailsService;

    private AmazonClient amazonClient;

    @Autowired
    ReviewController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }

    String s3Url = "https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value="upload review only content",notes = "사진을 제외한 콘텐츠 업로드")
    @PostMapping(value = "/uploadContent")
    public ResponseEntity<ReviewDTO> uploadReview(@RequestBody ReviewDTO reviewDTO){

        int usernum=userService.findUserByEmail(reviewDTO.getUser()).orElseThrow(()->new NoSuchDataException()).getUser_num();
        // confirm schedulenum

        Review review= Review.builder()
                            .seqnum(reviewDTO.getSeqnum())
                            .usernum(usernum)
                            .build();

        for (DetailDTO d: reviewDTO.getReviews()) {
            review.adddetails(d.toEntity());
        }

        reviewService.uploadReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="upload review only content",notes = "사진 업로드")
    @PostMapping(value = "/uploadPhoto")
    public ResponseEntity<ReviewDTO> uploadPhoto(@RequestParam String useremail, @RequestParam int seqnum, @RequestParam int schedulenum,
                                                   @RequestParam MultipartFile file){

        int usernum=userService.findUserByEmail(useremail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        //일단 schedulenum 으로만 update
        Details details = detailsService.loadDetailsBySchedulenum(schedulenum).orElseThrow(()->new NoSuchDataException("해당 리뷰 정보 없음."));

        ImagePath imagePath = this.amazonClient.uploadFile(file);
        details.addPhoto(Photo.builder().bucket(s3Url+imagePath.getDateName()+"/"+imagePath.getFileName()).build());

        detailsService.save(details);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/userload/{userEmail}")
//    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail){
//        int userNum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
//        List<Review> reviews = reviewService.loadReviewByUser(userNum);
//        return new ResponseEntity<>(reviews,HttpStatus.OK);
//    }
//
//
//
//    @GetMapping(value="/loadMainReviewByPaging/{page}")
//    public ResponseEntity<List<MainReviewDTO>> loadMainReviewByPaging(@PathVariable Integer page) throws IOException {
//        int size = 10;
//        PageRequest request = PageRequest.of(page, size, new Sort(Sort.Direction.ASC, "reviewnum"));
//        Page<Review> result = reviewService.loadMainReviewByPage(request);
//        List<Review> reviewList = result.getContent();
//        List<MainReviewDTO> reviewDTOList = new ArrayList<>();
//
//        for (Review r : reviewList) {
//            MainReviewDTO reviewDTO = r.toMainReviewDTO();
//            User user = userService.findUserByUsernum(r.getUsernum()).get();//.orElseThrow(()->new NoSuchDataException("유저 정보가 없습니다."));
//            reviewDTO.setUser(user.getEmail());
//            Thumb thumb = r.getThumb();
//            String photoUrl = thumb.getBucket();
//            reviewDTO.setPhotoDTO(photoUrl);
//            reviewDTOList.add(reviewDTO);
//        }
//
//        return new ResponseEntity<>(reviewDTOList,HttpStatus.OK);
//    }
}
