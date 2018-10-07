package org.soma.tripper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.Service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripperApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlaceService placeService;

    @Test
    public void contextLoads() {
        logger.info(placeService.findPlaceByNum(3).toString());
    }

}
