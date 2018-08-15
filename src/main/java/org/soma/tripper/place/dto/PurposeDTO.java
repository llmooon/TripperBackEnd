package org.soma.tripper.place.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurposeDTO {
    private int usernum; // if use userName, change!
    private int days;
    private int shopping;
    private int food;
    private int tourist;
    private int culture;
    private int Entertainment;
    private boolean withChildren;
    private boolean withElderly;
}
