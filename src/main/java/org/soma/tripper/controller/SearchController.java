package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.place.Service.SearchService;
import org.soma.tripper.place.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
@Api(value="Search Controller",description = "검색 API")

public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping(value = "{region}")
    @ApiOperation(value="Search Region",notes = "지역 검색")
    public ResponseEntity<Search> searchwhere(@PathVariable String region){
        Search search = searchService.SearchRegion(region,region);
        return new ResponseEntity<>(search, HttpStatus.OK);
    }
}
