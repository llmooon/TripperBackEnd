package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsServiceImpl implements DetailsService{

    @Autowired
    DetailsRepository detailsRepository;

    @Override
    public Optional<Details> loadDetailsBySchedulenum(int num) {
        return detailsRepository.findBySchedulenum(num);
    }

    @Override
    public Details save(Details details) {
        return detailsRepository.save(details);
    }
}
