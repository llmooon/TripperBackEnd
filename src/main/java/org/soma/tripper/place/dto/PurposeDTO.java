package org.soma.tripper.place.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurposeDTO {
    int days;
    int shopping;
    int food;
    int tourist;
    int culture;
    int Entertainment;
    boolean withChildren;
    boolean withElderly;


}
