package org.soma.tripper.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.soma.tripper.schedule.entity.Schedule;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ModifyDayDTO {
    private int daynum;
    private int day;
    private List<ModifyScheduleDTO> schedulelist;

}
