package org.soma.tripper.review.repository;

import org.soma.tripper.review.entity.Thumb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThumbRepository extends JpaRepository<Thumb,Integer> {
    Optional<Thumb> findByThumbnum(int num);
}
