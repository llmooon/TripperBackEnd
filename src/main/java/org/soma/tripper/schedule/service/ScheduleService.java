package org.soma.tripper.schedule.service;

import org.soma.tripper.schedule.entity.Schedule;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<Schedule> findAllSchedule();
    void saveScheduleList(List<Schedule> scheduleList);
    void saveSchedule(Schedule schedule);
    Optional<Schedule> findScheduleById(int id);
    void deleteSchedule(Schedule schedule);
}
