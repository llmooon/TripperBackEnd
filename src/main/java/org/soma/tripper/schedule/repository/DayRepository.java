package org.soma.tripper.schedule.repository;

import org.soma.tripper.schedule.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayRepository extends JpaRepository<Day,Integer> {
    Optional<Day> findDayByDaynum(int daynum);
}
