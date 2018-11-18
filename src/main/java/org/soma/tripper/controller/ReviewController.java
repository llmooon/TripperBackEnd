package org.soma.tripper.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SeqService;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.place.repository.SeqRepository;
import org.soma.tripper.review.dto.*;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.PhotoRepository;
import org.soma.tripper.review.service.AmazonClient;
import org.soma.tripper.review.service.DetailsService;
import org.soma.tripper.review.service.PhotoService;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.schedule.service.DayService;
import org.soma.tripper.schedule.service.ScheduleService;
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
import java.util.stream.Collectors;

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

    @Autowired
    DayService dayService;

    @Autowired
    PhotoService photoService;

    private AmazonClient amazonClient;

    @Autowired
    SeqRepository seqRepository;

    @Autowired
    ReviewController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }

    String s3Url = "https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @ApiOperation(value="review - write_review (리뷰 작성 로딩 화면) ")
//    @PostMapping(value = "/getUploadView/{reviewnum}")
//    public ResponseEntity<ReviewDTO> getuploadReviewView(@PathVariable int reviewnum){
//        Review review = reviewService.loadReviewById(reviewnum).orElseThrow(()->new NoSuchDataException("잘못된 리뷰 번호"));
//        ReviewDTO res = review.toReviewDTO();
//        return new ResponseEntity<>(res,HttpStatus.OK);
//    }

    @ApiOperation(value="review - write_review (리뷰 콘텐츠 작성) ")
    @PostMapping(value = "/uploadContent")
    public ResponseEntity<ReviewDTO> uploadReview(@RequestBody ReviewDTO reviewDTO){
        Review review = reviewService.loadReviewById(reviewDTO.getReviewnum()).orElseThrow(()->new NoSuchDataException("잘못된 리뷰 번호"));
        for(DetailDTO d:reviewDTO.getReviews()){
            Details details = detailsService.loadDetailsByDetailsnum(d.getDetailsnum()).orElseThrow(()->new NoSuchDataException());
            details.setContent(d.getContent());
        }
        review.setIsvalid(1);
        ReviewDTO res = reviewService.uploadReview(review).toReviewDTO();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @ApiOperation(value="review - delete_review (리뷰 콘텐츠 삭제) ")
    @PostMapping(value = "/deleteReview/{reviewnum}/{detailsnum}")
    public ResponseEntity<ReviewByDayDTO> deleteReview(@PathVariable int reviewnum,@PathVariable int detailsnum){
        Review review = reviewService.loadReviewById(reviewnum).orElseThrow(()->new NoSuchDataException("잘못된 리뷰 번호"));
        Details details = detailsService.loadDetailsByDetailsnum(detailsnum).orElseThrow(()->new NoSuchDataException(""));
        details.deleteContent();
        int photoSize = details.getPhotos().size();
        for(int i=0;i<photoSize;i++) {
            details.removePhoto(details.getPhotos().get(0));
        }
        int isvalid=0;
        for(Details d:review.getDetails()){
            if(d.getContent()!=null && d.getContent()!="" && d.getPhotos().size()!=0) isvalid=1;
        }
        if(isvalid==0){
            review.setIsvalid(0);
            reviewService.uploadReview(review);
            logger.info("delete!");
        }
        return LoadReviewByreviewnum(reviewnum);
    }


    @ApiOperation(value="review - write/Modify_review (리뷰 사진 업로드/수정) ")
    @PostMapping(value = "/uploadPhoto")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam int detailsnum, @RequestParam MultipartFile file){
        Details details = detailsService.loadDetailsByDetailsnum(detailsnum).orElseThrow(()->new NoSuchDataException("잘못된 detailsnum"));
        ImagePath imagePath = this.amazonClient.uploadFile(file);
        String imgUrl = s3Url+imagePath.getDateName()+"/thumb/"+imagePath.getFileName();
        Photo photo = Photo.builder().bucket(imgUrl).build();
        details.addPhoto(photo);
        detailsService.save(details);
        return new ResponseEntity<>(photo,HttpStatus.OK);
    }

    @ApiOperation(value="review - Detail_review (리뷰 제목 뒤 배경 사진 업로드. 근데 어디서 업로드?)")
    @PostMapping(value = "/uploadMainPhoto")
    public ResponseEntity<String> upload(@RequestParam String userEmail, @RequestParam int seqnum, @RequestParam MultipartFile file){
        int usernum=userService.findUserByEmail(userEmail).orElseThrow(()->new NoSuchDataException()).getUser_num();
        Review review = reviewService.loadReviewByUserAndSeq(usernum,seqnum).orElseThrow(()->new NoSuchDataException("없는 seqnum"));
        if(review.getThumb()!=null){
            String url=review.getThumb().getBucket();
            amazonClient.deleteFileFromS3Bucket(url);
        }
        ImagePath imagePath = this.amazonClient.uploadFile(file);
        String imgUrl = s3Url+imagePath.getDateName()+"/thumb/"+imagePath.getFileName();
        review.setThumb(Thumb.builder().bucket(imgUrl).build());
        reviewService.uploadReview(review);
        return new ResponseEntity<>(imgUrl,HttpStatus.OK);
    }

    @ApiOperation(value="review - Detail_review (리뷰 로드)")
    @GetMapping("/load/{reviewnum}")
    public ResponseEntity<ReviewByDayDTO> LoadReviewByreviewnum(@PathVariable int reviewnum){
        Review review = reviewService.loadReviewById(reviewnum).orElseThrow(()->new NoSuchDataException("잘못된 reviewnum"));
        review.setView(review.getView()+1);
        reviewService.uploadReview(review);
        List<Seq> seqList = seqRepository.findAll();
        Seq seq = seqService.loadSeq(review.getSeqnum()).orElseThrow(()->new NoSuchDataException("잘못된 seqnum"));

        List<DetailDTO> detail = review.toDetailDTO();
        List<DayDTO> dayDTOS = new ArrayList<>();
        List<ReadDetailDTO> readDetailDTOS = new ArrayList<>();

        for(int i=0;i<seq.getTotalday();i++) dayDTOS.add(DayDTO.builder().day(i+1).build()); //set Day size

        for (DetailDTO d: detail) {
            int day = dayService.findDaybyDaynum(d.getSchedule().getDaynum()).get().getDay();
            ReadDetailDTO rd =ReadDetailDTO.builder().content(d.getContent()).detailsnum(d.getDetailsnum()).photos(d.getPhotos()).schedule(d.getSchedule()).build();
            dayDTOS.get(day-1).addDetails(rd);
        }

        String user = userService.findUserByUsernum(review.getUsernum()).orElseThrow(()->new NoSuchDataException("잘못된 usernum")).getEmail();
        ReviewByDayDTO res;
        if(review.getThumb()!=null) {
            res = ReviewByDayDTO.builder()
                    .days(dayDTOS)
                    .seqnum(review.getSeqnum())
                    .user(user)
                    .title(seq.getTitle())
                    .thumb(review.getThumb().getBucket())
                    .build();
        }
        else{
            res = ReviewByDayDTO.builder()
                    .days(dayDTOS)
                    .seqnum(review.getSeqnum())
                    .user(user)
                    .title(seq.getTitle())
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
        Page<Review> result = reviewService.loadMainReviewByPage(request,1);
        List<Review> reviewList = result.getContent();
        List<MainReviewDTO> reviewDTOList = reviewList.stream()
                .map(review -> ReviewToMainReviewDTO(review))
                .collect(Collectors.toList());
        return new ResponseEntity<>(reviewDTOList,HttpStatus.OK);
    }
    @ApiOperation(value = "review - write_review (리뷰 사진 삭제)")
    @PostMapping(value="/deleteReviewPhoto")
    public void deleteReviewPhoto(@RequestBody DeletePhotoDTO deletePhotoDTO){
        Details detail = detailsService.loadDetailsByDetailsnum(deletePhotoDTO.getDetailsnum()).orElseThrow(()->new NoSuchDataException("error detailsnum"));
        for(int i=0;i<detail.getPhotos().size();i++){
            if(detail.getPhotos().get(i).getBucket().equals(deletePhotoDTO.getUrl())){
                photoService.delete(detail.getPhotos().remove(i));
                break;
            }
        }
        amazonClient.deleteFileFromS3Bucket(deletePhotoDTO.getUrl());
    }

    private MainReviewDTO ReviewToMainReviewDTO(Review r){
        return MainReviewDTO.builder()
                .reviewnum(r.getReviewnum())
                .photo(r.getThumb().getBucket())
                .time(r.getCreatedDate())
                .writer(userService.findUserByUsernum(r.getUsernum()).get().getEmail())
                .title(seqService.loadSeq(r.getSeqnum()).get().getTitle())
                .build();
    }


}