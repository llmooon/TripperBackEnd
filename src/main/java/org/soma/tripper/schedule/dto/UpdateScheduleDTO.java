package org.soma.tripper.schedule.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class UpdateScheduleDTO {
    private String username;
    private int seqnum;
    LocalDateTime startTime;
    private int day;
    private int beforePlacenum;
    private int newPlacenum;
}
