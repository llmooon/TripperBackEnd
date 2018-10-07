package org.soma.tripper.review.repository;

import org.soma.tripper.place.entity.Place;
import org.soma.tripper.review.dto.DetailDTO;
import org.soma.tripper.review.entity.Details;
import org.soma.tripper.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Details,Integer> {
    Optional<Details> findBySchedule(Schedule schedule);
    Page<Details> findBySchedule_Place(Pageable pageable, Place place);
}
