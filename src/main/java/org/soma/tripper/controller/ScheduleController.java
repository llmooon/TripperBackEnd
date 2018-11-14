package org.soma.tripper.controller;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.Service.SeqService;
import org.soma.tripper.place.dto.MLDTO;
import org.soma.tripper.place.dto.PlaceWithDistance;
import org.soma.tripper.place.dto.PurposeDTO;
import org.soma.tripper.place.dto.SeqDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.review.dto.MainReviewDTO;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.entity.Review;
import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.service.ReviewService;
import org.soma.tripper.review.service.ThumbService;
import org.soma.tripper.schedule.dto.MyScheduleDTO;
import org.soma.tripper.schedule.dto.RecomendedPlace;
import org.soma.tripper.schedule.dto.ScheduleDTO;
import org.soma.tripper.schedule.dto.UpdateScheduleDTO;
import org.soma.tripper.schedule.entity.Day;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.schedule.repository.DayRepository;
import org.soma.tripper.schedule.service.DayService;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PYTHON_SERVER_URL="http://djangoenv-env.f8jvbshimw.ap-northeast-2.elasticbeanstalk.com/recommend/2/?";
    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PlaceService placeService;

    @Autowired
    SeqService seqService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    DayService dayService;

    @Autowired
    ThumbService thumbService;


    @ApiOperation(value="input purpose for Trip",notes = "여행지를 리턴해줍니다. 일단 목적 요소들은 Int 형으로 입력, db는 완료 누른후.")
    @PostMapping("/inputPurpose")
    public ResponseEntity<SeqDTO> inputPurpose(@RequestBody PurposeDTO purposeDTO) throws Exception{
        User user = userService.findUserByEmail(purposeDTO.getUser()).orElseThrow(()->new NoSuchDataException("회원 정보가 없습니다."));

        MLDTO mldto = MLDTO.builder()
                .purposeDTO(purposeDTO)
                .user(user)
                .build();

        List<Day> dayList = sendML(mldto);
        Seq seq = Seq.builder()
                .dayList(dayList)
                .user(user)
                .region(purposeDTO.getRegion())
                .totalday(purposeDTO.getDays())
                .build();
        Seq result = seqService.insertSeq(seq);
        //didn't TestPlace!
        Review review= Review.builder()
                .seqnum(result.getSeqnum())
                .usernum(user.getUser_num())
                .isvalid(0)
                .build();
        for (Day d:dayList) {
            for(Schedule s:d.getSchedulelist()){
                review.adddetails(Details.builder().schedule(s).build());
            }
        }
        reviewService.uploadReview(review);
        return new ResponseEntity<>(seq.toDTO(),HttpStatus.OK);
    }

    @ApiOperation(value = "menu - My Schedule (유저별 스케쥴 로드)")
    @GetMapping("load/{userid}")
    public ResponseEntity<List<MyScheduleDTO>> loadSchedule(@PathVariable String userid){
        User user = userService.findUserByEmail(userid).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));
        List<Seq> seqList = seqService.loadSeqByUser(user).orElseThrow(()->new NoSuchDataException("빔"));
        List<MyScheduleDTO> res=new ArrayList<>();
        for (Seq seq:seqList ) {
            res.add(seq.toMyDTO());
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @ApiOperation(value="update Schedule", notes = "시간 없데이트는 나중에... 흑")
    @PutMapping("update")
    public ResponseEntity<SeqDTO> updateSchedule(@RequestBody UpdateScheduleDTO updateScheduleDTO){
       Seq seq = seqService.loadSeq(updateScheduleDTO.getSeqnum()).orElseThrow(()-> new NoSuchDataException("없는 seqnum"));
       Place newplace = placeService.findPlaceByNum(updateScheduleDTO.getNewPlacenum()).orElseThrow(()-> new NoSuchDataException("없는 placenum"));
       List<Schedule> scheduleList = seq.getDayList().get(updateScheduleDTO.getDay()-1).getSchedulelist();
        for (Schedule s:scheduleList) {
            if(s.getPlace().getPlace_num()==updateScheduleDTO.getBeforePlacenum()){
                s.setPlace(newplace);
               // s.setStartTime(updateScheduleDTO.getStartTime());
                break;
            }
        }
        // later ml send!
       seqService.modifySeq(seq);
        return new ResponseEntity<>(seq.toDTO(),HttpStatus.OK);
    }


    @PutMapping("/inputScheduleName")
    public ResponseEntity<SeqDTO> inputScheduleName(@RequestBody ScheduleDTO scheduleDTO){
        Seq seq = seqService.loadSeq(scheduleDTO.getSeqnum()).orElseThrow(()->new NoSuchDataException("없는 seqnum"));
        seq.setTitle(scheduleDTO.getScheduleName());
        seqService.modifySeq(seq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/SearchingByCategory/{version}/{seqnum}/{daynum}/{page}")
    @ApiOperation(value="version 값에 따른 카테고리별 장소 반환/ 관광지 :1 //맛집 :2 // 스포츠 :3 // 쇼핑 :4 // 숙박:5 // 공원 :6 // 야경 :7  ")
    public ResponseEntity<List<RecomendedPlace>> searchingByCategory(@PathVariable Integer version, @PathVariable Integer seqnum, @PathVariable Integer daynum, @PathVariable Integer page){
        List<Schedule> scheduleList = dayService.findDaybySeqnumAndDay(seqnum,daynum).get().getSchedulelist();

        //스케쥴 일정의 평균 위도, 경도를 중심으로 카테고리별 장소 추천
        double AverageLA=0, AverageLO=0;
        for (Schedule s: scheduleList) {
            AverageLA += s.getPlace().getLatitude();
            AverageLO += s.getPlace().getLongtitude();
        }
        AverageLA= AverageLA/scheduleList.size();
        AverageLO = AverageLO/scheduleList.size();

        PageRequest request;
        request= PageRequest.of(page,20);
        List<Object[]> placeList = placeService.getPlaceByVersion(version,AverageLA,AverageLO,request);
        List<RecomendedPlace> recomendedPlaces = new ArrayList<>();
        for(Object[] p : placeList){
            String img = thumbService.findThumbByNum((Integer) p[10]).get().getBucket();
            RecomendedPlace recomendedPlace = RecomendedPlace.builder().placenum((Integer) p[0]).city((String)p[1]).name((String)p[6]).picture(img).build();
            recomendedPlaces.add(recomendedPlace);
        }
        return new ResponseEntity<>(recomendedPlaces,HttpStatus.OK);
    }

    private List<Day> sendML(MLDTO mldto) throws Exception{
        String url = PYTHON_SERVER_URL+"day="+mldto.getDays()+"&shopping="+mldto.getShopping()
                +"&food="+mldto.getFood()+"&tourist="+mldto.getTourist()+"&culture="+mldto.getCulture()+"&entertainment="+mldto.getEntertainment();
        URI uri = URI.create(url);
        RestTemplate restTemplate=new RestTemplate();
        String responseString = restTemplate.getForObject(uri,String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(responseString);
        JSONArray json = (JSONArray) jsonObject.get("result");

        List<Day> dayList=new ArrayList<>();
        List<List<Schedule>> scheduleList = new ArrayList<>();

        for(int i=0;i<json.size();i++){
            scheduleList.add(new ArrayList<>());
            JSONObject daylistjson = (JSONObject) json.get(i);
            JSONArray mldaylist = (JSONArray) daylistjson.get("course");

            for(int j=0;j<mldaylist.size();j++){
                JSONObject tmp = (JSONObject) mldaylist.get(j);
                ZonedDateTime tmptime=ZonedDateTime.parse((String)tmp.get("recommend_time"));
                LocalDateTime date1=tmptime.toLocalDateTime();
                int place_num= ((Long)tmp.get("trip_id")).intValue();
                Place place=placeService.findPlaceByNum(place_num).orElseThrow(()->new NoSuchDataException("wrong ML place_nium"));

                Schedule schedule = Schedule.builder()
                        .daynum(i+1)
                        .startTime(date1)
                        .place(place)
                        .build();
                scheduleList.get(i).add(schedule);
            }
            Day day = Day.builder().schedulelist(scheduleList.get(i))
                    .day(i+1)
                    .build();
            dayList.add(day);
        }
        return dayList;
    }
}
