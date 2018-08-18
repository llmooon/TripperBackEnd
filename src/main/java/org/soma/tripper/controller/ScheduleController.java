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
import org.soma.tripper.place.dto.MLDTO;
import org.soma.tripper.place.dto.PurposeDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @ApiOperation(value="input purpose for Trip",notes = "여행지를 리턴해줍니다. 일단 목적 요소들은 Int 형으로 입력받지만, ml이랑 한번 이야기 해 봐야할듯.")
    @PostMapping("/inputPurpose")
    public ResponseEntity<Object> sendPurpose(@RequestBody PurposeDTO purposeDTO){
        User user = userService.findUserByUsernum(purposeDTO.getUsernum()).orElseThrow(()->new NoSuchDataException("회원 정보가 없습니다."));

        MLDTO mldto = MLDTO.builder()
                .purposeDTO(purposeDTO)
                .user(user)
                .build();

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

        List<Integer> placeNum = (List<Integer>) (((ResponseEntity) obj).getBody());
        List<Place> placeList = new ArrayList<>();
        for(int i : placeNum){
            placeList.add(placeService.findPlaceByNum(i).get());
        }
        return new ResponseEntity<>(placeList,HttpStatus.OK);

    }

    @ApiOperation(value="Test with ML Server",notes = "테스트용입니다.")
    @GetMapping("/testML")
    public ResponseEntity<List<Integer>> testsendAllSchedule(){
        ArrayList<Integer> placeList = new ArrayList<>();
        placeList.add(1);
        placeList.add(2);
        placeList.add(3);
        return new ResponseEntity<>(placeList,HttpStatus.OK);
    }
}
