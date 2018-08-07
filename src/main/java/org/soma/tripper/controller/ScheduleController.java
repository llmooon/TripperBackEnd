package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.dto.PurposeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/make")
    public ResponseEntity<PurposeDTO> makeSchedule(@RequestBody PurposeDTO purposeDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
