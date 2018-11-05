package org.soma.tripper.schedule.service;

import org.soma.tripper.schedule.entity.Day;
import org.soma.tripper.schedule.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DayServiceImpl implements DayService{

    @Autowired
    DayRepository dayRepository;

    @Override
    public Optional<Day> findDaybyDaynum(int num) {
        return dayRepository.findDayByDaynum(num);
    }


}
