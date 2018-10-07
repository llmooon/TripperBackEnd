package org.soma.tripper.review.service;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DetailsService {
    Optional<Details> loadDetailsBySchedule(Schedule schedule);
    Details save(Details details);
    Page<Details> loadDetailsByPlace(Pageable pageable, Place place);
}
