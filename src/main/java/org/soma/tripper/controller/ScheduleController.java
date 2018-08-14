package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.dto.PurposeDTO;
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

import java.util.List;

@RestController
@RequestMapping("/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PYTHON_SERVER_URL="http://localhost:8080/schedule//sendAllSchedule";
    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/inputPurpose")
    public ResponseEntity<Object> sendPurpose(@RequestBody PurposeDTO purposeDTO){
       // int usernum = userService.findUserByEmail(userid).getUser_num();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectionRequestTimeout(5000);
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        Object obj = restTemplate.getForObject(PYTHON_SERVER_URL,List.class);

        //convert Object to List<Schedule> and save schedule

        return new ResponseEntity<>(obj,HttpStatus.OK);
    }

    @GetMapping("/sendAllSchedule")
    public ResponseEntity<List<Schedule>> testsendAllSchedule(){
        List<Schedule> scheduleList = scheduleService.findAllSchedule();
        return new ResponseEntity<>(scheduleList,HttpStatus.OK);
    }
}
