package org.soma.tripper.place.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.place.Service.SearchService;
import org.soma.tripper.place.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());




}
