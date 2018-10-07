package org.soma.tripper.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SeqService;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.review.dto.*;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.DetailsService;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.schedule.entity.Schedule;
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

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    PlaceService placeService;

    @Autowired
    SeqService seqService;

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

        Review review= Review.builder()
                            .seqnum(reviewDTO.getSeqnum())
                            .usernum(usernum)
                            .build();

        for (DetailDTO d: reviewDTO.getReviews()) {
            review.adddetails(d.toEntity(scheduleService.findScheduleById(d.getSchedulenum()).orElseThrow(()->new NoSuchDataException("error schedulenum!"))));
        }
        reviewService.uploadReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="upload review only content",notes = "사진 업로드")
    @PostMapping(value = "/uploadPhoto")
    public ResponseEntity<ReviewDTO> uploadPhoto(@RequestParam String userEmail, @RequestParam int seqnum, @RequestParam int schedulenum,
                                                   @RequestParam MultipartFile file){

        int usernum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        //일단 schedulenum 으로만 update
        Schedule schedule = scheduleService.findScheduleById(schedulenum).orElseThrow(()->new NoSuchDataException("잘못된 schedule"));
        Details details = detailsService.loadDetailsBySchedule(schedule).orElseThrow(()->new NoSuchDataException("해당 리뷰 정보 없음."));
        ImagePath imagePath = this.amazonClient.uploadFile(file);
        details.addPhoto(Photo.builder().bucket(s3Url+imagePath.getDateName()+"/"+imagePath.getFileName()).build());
        detailsService.save(details);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="upload Main Photo",notes = "대표 리뷰 사진 등록/수정")
    @PostMapping(value = "/uploadMainPhoto")
    public ResponseEntity<ReviewDTO> upload(@RequestParam String userEmail, @RequestParam int seqnum, @RequestParam MultipartFile file){
        int usernum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        Review review = reviewService.loadReviewByUserAndSeq(usernum,seqnum).orElseThrow(()->new NoSuchDataException("없는 seqnum"));
        if(review.getThumb()!=null){
            String url=review.getThumb().getBucket();
            amazonClient.deleteFileFromS3Bucket(url);
        }
        ImagePath imagePath = this.amazonClient.uploadFile(file);
        review.setThumb(Thumb.builder().bucket(s3Url+imagePath.getDateName()+"/thumb/"+imagePath.getFileName()).build());
        reviewService.uploadReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/userload/{userEmail}")
    public ResponseEntity<List<Review>> userLoad(@PathVariable String userEmail){
        int userNum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        List<Review> reviews = reviewService.loadReviewByUser(userNum).orElseThrow(()->new NoSuchDataException("작성된 리뷰가 없습니다."));
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }


    @GetMapping("/load/{reviewnum}")
    public ResponseEntity<ReviewByDayDTO> LoadReviewByreviewnum(@PathVariable int reviewnum){
        Review review = reviewService.loadReviewById(reviewnum).orElseThrow(()->new NoSuchDataException("잘못된 reviewnum"));
        Seq seq = seqService.loadSeq(review.getSeqnum()).orElseThrow(()->new NoSuchDataException("잘못된 seqnum"));


        List<DetailDTO> detail = review.toDetailDTO();
        List<DayDTO> dayDTOS = new ArrayList<>();
        List<ReadDetailDTO> readDetailDTOS = new ArrayList<>();
        int days=0;
        for (DetailDTO d: detail) {
            logger.info(d.toString());
            Schedule schedule =scheduleService.findScheduleById(d.getSchedulenum()).orElseThrow(()-> new NoSuchDataException("error schedule num"));
            int day = schedule.getDaynum();
            ReadDetailDTO rd =ReadDetailDTO.builder().content(d.getContent()).photos(d.getPhotos()).schedule(schedule).build();

            if(day!=days){
                dayDTOS.add(DayDTO.builder().day(day).build());
                days++;
            }
            dayDTOS.get(day-1).addDetails(rd);
        }

        String user = userService.findUserByUsernum(review.getUsernum()).orElseThrow(()->new NoSuchDataException("잘못된 usernum")).getEmail();
        ReviewByDayDTO res;
        if(review.getThumb()!=null) {
            res = ReviewByDayDTO.builder()
                    .days(dayDTOS)
                    .seqnum(review.getSeqnum())
                    .user(user)
                    .thumb(review.getThumb().getBucket())
                    .build();
        }
        else{
            res = ReviewByDayDTO.builder()
                    .days(dayDTOS)
                    .seqnum(review.getSeqnum())
                    .user(user)
                    .build();
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="페이지당 20개씩. 시간순은 version 값 0, 조회수는 version 값 1로 설정. ",notes = "메인 화면 리뷰 불러오기")
    @GetMapping(value="/loadMainReviewByPaging/{page}/{version}")
    public ResponseEntity<List<MainReviewDTO>> loadMainReviewByPaging(@PathVariable Integer page , @PathVariable Integer version) throws IOException {
        int size = 20;
        PageRequest request;
        if(version==0) request=PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "reviewnum"));
        else request=PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "view"));
        Page<Review> result = reviewService.loadMainReviewByPage(request);
        List<Review> reviewList = result.getContent();
        List<MainReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review r : reviewList) {
            reviewDTOList.add(MainReviewDTO.builder()
                    .reviewnum(r.getReviewnum())
                    .photo(r.getThumb().getBucket())
                    .time(r.getCreatedDate())
                    .writer(userService.findUserByUsernum(r.getUsernum()).get().getEmail())
                    .title(seqService.loadSeq(r.getSeqnum()).get().getTitle())
                    .build());
        }
        return new ResponseEntity<>(reviewDTOList,HttpStatus.OK);
    }


}

// 1. 한글 파일 s3 upload 안됨.
// 2. s3내 삭제 안됨.