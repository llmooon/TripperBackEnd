package org.soma.tripper.review.service;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.review.repository.DetailsRepository;
import org.soma.tripper.schedule.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsServiceImpl implements DetailsService{

    @Autowired
    DetailsRepository detailsRepository;

    @Override
    public Optional<Details> loadDetailsBySchedule(Schedule schedule) {
        return detailsRepository.findBySchedule(schedule);
    }

    @Override
    public Details save(Details details) {
        return detailsRepository.save(details);
    }

    @Override
    public Page<Details> loadDetailsByPlace(Pageable pageable, Place place) {
        return detailsRepository.findBySchedule_Place(pageable,place);
    }

    @Override
    public Optional<Details> loadDetailsByDetailsnum(int id) {
        return detailsRepository.findByDetailsnum(id);
    }
}
