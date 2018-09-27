package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Details;

import java.util.Optional;

public interface DetailsService {
    Optional<Details> loadDetailsBySchedulenum(int num);
    Details save(Details details);
}
