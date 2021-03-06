package org.soma.tripper.schedule.service;

import org.soma.tripper.schedule.entity.Day;

import java.util.Optional;

public interface DayService {
    Optional<Day> findDaybyDaynum(int num);
    Optional<Day> findDaybySeqnumAndDay(int seqnum, int day);
    void deleteDay(Day day);
}
