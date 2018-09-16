package org.soma.tripper.controller;

import com.amazonaws.services.mq.model.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PYTHON_SERVER_URL="http://localhost:8080/schedule/testML";
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
    public ResponseEntity<SeqDTO> inputPurpose(@RequestBody PurposeDTO purposeDTO){
        User user = userService.findUserByEmail(purposeDTO.getUser()).orElseThrow(()->new NoSuchDataException("회원 정보가 없습니다."));

        MLDTO mldto = MLDTO.builder()
                .purposeDTO(purposeDTO)
                .user(user)
                .build();

        List<Schedule> scheduleList = makeSchedule(sendML(mldto));
        Seq seq = Seq.builder()
                .schedulelist(scheduleList)
                .user(user)
                .build();

        return new ResponseEntity<>(seq.toDTO(),HttpStatus.OK);
    }

    @ApiOperation(value="add Schedule",notes = "Zepplin Schedule 화면에서 스케줄 조정 후 완료 누른후.")
    @PostMapping("add")
    public ResponseEntity<Seq> addSchedule(@RequestBody SeqDTO seqDTO){
        User user = userService.findUserByEmail(seqDTO.getUser()).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));

        Seq seq = Seq.builder()
                .schedulelist(seqDTO.getSchedulelist())
                .user(user)
                .build();

        seqService.insertSeq(seq);
        return new ResponseEntity<>(seq,HttpStatus.OK);
    }

    @ApiOperation(value="update Schedule",notes = "계획 수정")
    @PostMapping("/update")
    public void updateSchedule(@RequestBody SeqDTO seqDTO){
        User user = userService.findUserByEmail(seqDTO.getUser()).orElseThrow(()->new NoSuchDataException("잘못된 회원 정보"));
        Seq seq = Seq.builder()
                .schedulelist(seqDTO.getSchedulelist())
                .user(user)
                .seqnum(seqDTO.getSeqnum())
                .build();
        seqService.modifySeq(seq);
    }

    @ApiOperation(value="Test with ML Server",notes = "테스트용입니다.")
    @GetMapping("/testML")
    public ResponseEntity<List<Integer>> testsendAllSchedule(){
        List<Place> places=placeService.getAllPlace();
        ArrayList<Integer> placeList = new ArrayList<>();
        for(Place p : places){
            placeList.add(p.getPlace_num());
        }
        return new ResponseEntity<>(placeList,HttpStatus.OK);
    }

    private List<Integer> sendML(MLDTO mldto){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectionRequestTimeout(5000);
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        Object obj = restTemplate.getForEntity(PYTHON_SERVER_URL,List.class,mldto);
        return (List<Integer>) (((ResponseEntity) obj).getBody());
    }

    private List<Schedule> makeSchedule(List<Integer> placeNum){
        List<Schedule> scheduleList = new ArrayList<>();
        int nowday=1,nowcnt=0;

        for(int i : placeNum){
            //Date d = new Date;
            Schedule schedule = Schedule.builder()
                    .day(nowday)
                    .place(placeService.findPlaceByNum(i).get())
                    .build();
            scheduleList.add(schedule);

            nowcnt++;
            if(nowcnt>=3){nowcnt=0;nowday++;}
        }
        return scheduleList;
    }
}
