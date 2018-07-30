package org.soma.tripper.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @GetMapping(value = "{region}")
    public void searchwhere(@PathVariable String region){

    }
}
