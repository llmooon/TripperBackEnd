package org.soma.tripper.review.service;

import org.soma.tripper.review.entity.Photo;
import org.soma.tripper.review.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoRepository photoRepository;
    @Override
    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }
}
