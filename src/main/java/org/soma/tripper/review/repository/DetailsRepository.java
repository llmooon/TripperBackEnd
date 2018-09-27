package org.soma.tripper.review.repository;

import org.soma.tripper.review.dto.DetailDTO;
import org.soma.tripper.review.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Details,Integer> {
    Optional<Details> findBySchedulenum(int num);
}
