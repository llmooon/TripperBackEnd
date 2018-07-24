package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.domain.User;
import org.soma.tripper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="sampleController",description = "샘플 API")
public class SampleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    @ApiOperation(value="sample api",notes = "테스트 용 API")
    public String helloworld(){
        logger.info("run helloworld");
        return "hello, world!";
    }
}
