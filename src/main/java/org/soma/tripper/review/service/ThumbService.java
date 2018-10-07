package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.ThumbRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface ThumbService {
    Optional<Thumb> findThumbByNum(int num);
}
