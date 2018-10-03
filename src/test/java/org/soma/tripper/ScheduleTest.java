package org.soma.tripper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.Service.PlaceService;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PlaceService placeService;


    Schedule schedule;

    @Test
    public void test(){
        List<Schedule>  scheduleList = scheduleService.findAllSchedule();
        logger.info(scheduleList.toString());
    }
}
