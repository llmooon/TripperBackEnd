package org.soma.tripper.schedule.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class ScheduleDTO {
    String username;
    int seqnum;
    String scheduleName;
}
