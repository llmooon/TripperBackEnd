package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.dto.PurposeDTO;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/schedule")
@Api(value="Schedule Controller",description = "스케쥴 API")

public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping("/purpose")
    public ResponseEntity<PurposeDTO> sendPurpose(@RequestBody PurposeDTO purposeDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/makeSchedule/{user}")
    public ResponseEntity makeSchedule(@RequestBody List<Schedule> scheduleList, @PathVariable String user){
        int usernum = userService.findUserByEmail(user).getUser_num();

        for (Schedule s: scheduleList) {
            //스케줄 저장을 여기서? 아님 seq 저장하면서?
        }

        return new ResponseEntity(HttpStatus.OK);
    }


}
