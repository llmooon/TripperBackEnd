package org.soma.tripper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void test(){

        double AverageLA= 35.20377;
        double AverageLO = 129.086;

        PageRequest request;
        request= PageRequest.of(0,50);

        List<Object> list = placeRepository.getCategory(2,AverageLA,AverageLO,request);
        logger.info(list.get(0).getClass().toString());

        logger.info(list.toString());
    }
}
