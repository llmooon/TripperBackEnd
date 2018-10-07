package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Thumb;
import org.soma.tripper.review.repository.ThumbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThumbServiceImpl implements ThumbService{
    @Autowired
    ThumbRepository thumbRepository;

    @Override
    public Optional<Thumb> findThumbByNum(int num) {
        return thumbRepository.findByThumbnum(num);
    }
}
