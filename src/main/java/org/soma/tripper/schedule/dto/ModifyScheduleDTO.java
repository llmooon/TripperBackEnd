package org.soma.tripper.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.soma.tripper.place.entity.Place;

import java.time.LocalDateTime;

@Getter
@ToString
public class ModifyScheduleDTO {
    //int schedulenum;
    LocalDateTime startTime;
    int place_num;

}
