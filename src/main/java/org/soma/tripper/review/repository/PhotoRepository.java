package org.soma.tripper.review.repository;

import org.soma.tripper.review.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo,Integer> {
}
