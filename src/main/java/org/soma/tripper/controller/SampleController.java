package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="sampleController",description = "샘플 API")
public class SampleController {
    @GetMapping("/hello")
    @ApiOperation(value="sample api",notes = "테스트 용 API")
    public String helloworld(){
        return "hello, world!";
    }
}
