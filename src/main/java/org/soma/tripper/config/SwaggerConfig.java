package org.soma.tripper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())//PathSelectors.ant("/api/**")
                .build()
                //.apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,getArrayList())
                .globalResponseMessage(RequestMethod.POST,getArrayList());

    }

    private ArrayList<ResponseMessage> getArrayList() {
        ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();

        lists.add(new ResponseMessageBuilder().code(201).message("생성 성공").responseModel(new ModelRef("Create Success")).build());
        lists.add(new ResponseMessageBuilder().code(400).message("나쁜 요청").responseModel(new ModelRef("Bad Request")).build());
        lists.add(new ResponseMessageBuilder().code(403).message("권한 에러").responseModel(new ModelRef("Forbidden")).build());
        lists.add(new ResponseMessageBuilder().code(409).message("중복").responseModel(new ModelRef("conflict")).build());

        lists.add(new ResponseMessageBuilder().code(500).message("서버 에러").responseModel(new ModelRef("Error")).build());
        return lists;
    }



}
