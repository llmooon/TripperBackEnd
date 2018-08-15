package org.soma.tripper.schedule.service;

import org.soma.tripper.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAllSchedule();
}
