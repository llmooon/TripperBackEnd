package org.soma.tripper.controller;

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
import org.soma.tripper.place.dto.PurposeDTO;
import org.soma.tripper.place.dto.SeqDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.schedule.dto.RecomendedPlace;
import org.soma.tripper.schedule.dto.ScheduleDTO;
import org.soma.tripper.schedule.entity.Day;
import org.soma.tripper.schedule.entity.Schedule;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PYTHON_SERVER_TEST_URL="http://localhost:8080/schedule/testML";
    private static final String PYTHON_SERVER_URL="http://djangoenv-env.f8jvbshimw.ap-northeast-2.elasticbeanstalk.com/recommend/2/?";
    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PlaceService placeService;

    @Autowired
    SeqService seqService;

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
                .build();

        return new ResponseEntity<>(seq.toDTO(),HttpStatus.OK);
    }

    @ApiOperation(value = "load Schedule",notes = "유저별 스케쥴 로드")
    @GetMapping("load/{userid}")
    public ResponseEntity<List<SeqDTO>> loadSchedule(@PathVariable String userid){
        User user = userService.findUserByEmail(userid).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));
        List<Seq> seqList = seqService.loadSeqByUser(user).orElseThrow(()->new NoSuchDataException("빔"));
        List<SeqDTO> seqDTOList=new ArrayList<>();
        for (Seq seq:seqList ) {
            seqDTOList.add(seq.toDTO());
        }
        return new ResponseEntity<>(seqDTOList,HttpStatus.OK);
    }

    @ApiOperation(value="add Schedule",notes = "Zepplin Schedule 화면에서 스케줄 조정 후 완료 누른후.")
    @PostMapping("add")
    public ResponseEntity<SeqDTO> addSchedule(@RequestBody SeqDTO seqDTO){
        User user = userService.findUserByEmail(seqDTO.getUser()).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));

        Seq seq = Seq.builder()
                .dayList(seqDTO.getDayList())
                .user(user)
                .build();

        Seq result  = seqService.insertSeq(seq);
        return new ResponseEntity<>(result.toDTO(),HttpStatus.OK);
    }

    @ApiOperation(value="update Schedule",notes = "계획 수정")
    @PostMapping("/update")
    public ResponseEntity<SeqDTO> updateSchedule(@RequestBody SeqDTO seqDTO){
        User user = userService.findUserByEmail(seqDTO.getUser()).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));
        Seq info = seqService.loadSeq(seqDTO.getSeqnum()).orElseThrow(()->new NoSuchDataException("없는 정보"));
        seqService.deleteSeq(seqDTO.getSeqnum());

        Seq seq = seqService.insertSeq(
                Seq.builder()
                .user(user)
                .dayList(seqDTO.getDayList())
                .fromdate(seqDTO.getFromdate())
                .toDate(seqDTO.getToDate())
                .build()
        );

       return new ResponseEntity<>(seq.toDTO(),HttpStatus.OK);
    }

    @PutMapping("/inputScheduleName")
    public ResponseEntity<SeqDTO> inputScheduleName(@RequestBody ScheduleDTO scheduleDTO){
        Seq seq = seqService.loadSeq(scheduleDTO.getSeqnum()).orElseThrow(()->new NoSuchDataException("없는 seqnum"));
        seq.setTitle(scheduleDTO.getScheduleName());
        seqService.modifySeq(seq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="Test with ML Server",notes = "테스트용입니다.")
    @GetMapping("/testML")
    public ResponseEntity<List<Integer>> testsendAllSchedule(){
        List<Place> places=placeService.getAllPlace();
        ArrayList<Integer> placeList = new ArrayList<>();
        int cnt=0;
        for(Place p : places){
            placeList.add(p.getPlace_num());
            cnt++;
            if(cnt>10) break;
        }
        return new ResponseEntity<>(placeList,HttpStatus.OK);
    }

    @GetMapping("/SearchingByCategory/{version}/{beforePlaceNum}/{page}")
    @ApiOperation(value="version 값에 따른 카테고리별 장소 반환/ 관광지 :1 //맛집 :2 // 스포츠 :3 // 쇼핑 :4 // 숙박:5 // 공원 :6 // 야경 :7  ")
    public ResponseEntity<List<RecomendedPlace>> searchingByCategory(@PathVariable Integer version, @PathVariable Integer beforePlaceNum, @PathVariable Integer page){
        Place place = placeService.findPlaceByNum(beforePlaceNum).orElseThrow(()-> new NoSuchDataException("잘못된 placenum"));
        PageRequest request;
        request= PageRequest.of(page,20);
        Page<Place> placeList = placeService.getPlaceByVersion(request,version);
        List<RecomendedPlace> recomendedPlaces = new ArrayList<>();
        for(Place p : placeList){
            RecomendedPlace recomendedPlace = RecomendedPlace.builder().placenum(p.getPlace_num()).city(p.getCity()).name(p.getName()).picture(p.getThumb().getBucket()).build();
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

//        ZonedDateTime d = ZonedDateTime.parse((String)((JSONObject)json.get(0)).get("recommend_time"));
//        LocalDateTime beforeday = d.toLocalDateTime();
//        int cnt=0;
//
//        for(int i=0;i<json.size();i++){
//            JSONObject tmp = (JSONObject) json.get(i);
//
//            ZonedDateTime tmptime=ZonedDateTime.parse((String)tmp.get("recommend_time"));
//            LocalDateTime date1=tmptime.toLocalDateTime();
//            int place_num= ((Long)tmp.get("trip_id")).intValue();
//            Place place=placeService.findPlaceByNum(place_num).orElseThrow(()->new NoSuchDataException("wrong place_nium"));
//
//            if(date1.getDayOfMonth()!=beforeday.getDayOfMonth()) {
//                cnt++;
//                Day day = Day.builder().schedulelist(scheduleList.get(cnt-1))
//                        .day(cnt)
//                        .build();
//                dayList.add(day);
//                scheduleList.add(new ArrayList<>());
//            }
//            beforeday=date1;
//
//            Schedule schedule = Schedule.builder()
//                    .daynum(cnt)
//                    .startTime(date1)
//                    .place(place)
//                    .build();
//            scheduleList.get(cnt).add(schedule);
//        }
//        cnt++;
//        Day day = Day.builder().schedulelist(scheduleList.get(cnt-1))
//                .day(cnt)
//                .build();
//        dayList.add(day);
//        scheduleList.add(new ArrayList<>());
        return dayList;
    }
}
