package org.soma.tripper.place.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurposeDTO {
    private String user; // if use userName, change!
    private int region;
    private int days;
    private int shopping;
    private int food;
    private int tourist;
    private int culture;
    private int Entertainment;
    private boolean withChildren;
    private boolean withElderly;
}
