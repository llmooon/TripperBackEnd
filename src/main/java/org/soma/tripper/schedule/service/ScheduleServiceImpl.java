package org.soma.tripper.schedule.service;

import io.swagger.annotations.Authorization;
import org.soma.tripper.schedule.entity.Schedule;
import org.soma.tripper.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);

    }

    @Override
    public List<Schedule> findAllSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void saveScheduleList(List<Schedule> scheduleList) {
        for (Schedule s: scheduleList ) {
            scheduleRepository.save(s);
        }
    }

    @Override
    public Optional<Schedule> findScheduleById(int id) {
        return scheduleRepository.findById(id);
    }
}
