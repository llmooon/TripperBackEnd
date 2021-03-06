package org.soma.tripper.airport;

import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.airport.Entity.Airport;
import org.soma.tripper.airport.dto.SearchAirPortDto;
import org.soma.tripper.airport.service.AirPortService;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.review.entity.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/airport")
public class AirPortController {

    @Autowired
    AirPortService airPortService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String getCodeUrl="http://openapi.airport.co.kr/service/rest/AirportCodeList/getAirportCodeList?numOfRows=949";
    private String searchUrl="http://openapi.airport.co.kr/service/rest/DflightScheduleList/get DflightScheduleList?";
    private String key = "&ServiceKey=P1c4m2lUvkf3mlKVA9W%2BGq6Di3Gs1HiMcyx9HukfeFP8Q7hVJ%2FNJ0u1EFiB7ql728phCz8%2Fc3R6hmPjPbEMeqA%3D%3D";

    @GetMapping(value="/getCode")
    @ApiOperation(value="Don't touch", notes = "don't touch")
    public void getcode() throws Exception{
        URI uri = URI.create(getCodeUrl+key);
        RestTemplate restTemplate = new RestTemplate();
        String responseString = restTemplate.getForObject(uri,String.class);
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject)jsonParser.parse(responseString);
        JSONObject json = (JSONObject) jsonObject.get("response");
        JSONObject jsonbody = (JSONObject) json.get("body");
        JSONObject jsonitems = (JSONObject) jsonbody.get("items");
        JSONArray jsonArray = (JSONArray) jsonitems.get("item");
        for(int i=0; i<jsonArray.size();i++){
            JSONObject code = (JSONObject) jsonArray.get(i);
            airPortService.saveCode(
                    Airport.builder()
                            .code((String)code.get("cityCode"))
                            .English((String)code.get("cityEng"))
                            .korea((String)code.get("cityKor"))
                    .build()
            );
        }
    }

    @GetMapping("/findCode/{city}")
    @ApiOperation(value="send code",notes="get code")
    public ResponseEntity<List<Airport>> sendcode(@PathVariable  String city){

        PageRequest request;
        request=PageRequest.of(0, 1000);
        Page<Airport> res=airPortService.findCode(request,city);
        logger.info(res.getContent().toString());
        return new ResponseEntity<>(res.getContent(),HttpStatus.OK);
    }

    //didn't TestPlace.
    @GetMapping("/findAirport/{deptCityCode}/{arrvCityCode}/{searchDate}")
    @ApiOperation(value="항공권 검색. deptcityCode=출발 도시 코드, arrvCityCode=도착 도시 코드, searchDate=yyyymmdd 형식으로.")
    public ResponseEntity<List<SearchAirPortDto>> searchAirport(@PathVariable String deptCityCode, @PathVariable String arrvCityCode, @PathVariable String searchDate){
        URI uri = URI.create(searchUrl+"schDate="+searchDate+"&schDeptCityCode="+deptCityCode+"schArrvCityCode="+arrvCityCode+key);
        RestTemplate restTemplate = new RestTemplate();
        String responseString = restTemplate.getForObject(uri,String.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
