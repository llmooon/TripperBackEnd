package org.soma.tripper.controller;

import antlr.PythonCharFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.dto.MLDTO;
import org.soma.tripper.place.dto.PurposeDTO;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.schedule.service.ScheduleService;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @ApiOperation(value="input purpose for Trip",notes = "여행지를 리턴해줍니다.")
    @PostMapping("/inputPurpose")
    public ResponseEntity<Object> sendPurpose(@RequestBody PurposeDTO purposeDTO){
        Optional<User> user = userService.findUserByUsernum(purposeDTO.getUsernum());
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

        List<Integer> placeList = (List<Integer>) (((ResponseEntity) obj).getBody());
        return new ResponseEntity<>(placeList,HttpStatus.OK);

    }

    @ApiOperation(value="Test with ML Server",notes = "테스트용입니다.")
    @GetMapping("/testML")
    public ResponseEntity<List<Integer>> testsendAllSchedule(){
        ArrayList<Integer> placeList = new ArrayList<>();
        placeList.add(1);
        placeList.add(3);
        return new ResponseEntity<>(placeList,HttpStatus.OK);
    }
}
