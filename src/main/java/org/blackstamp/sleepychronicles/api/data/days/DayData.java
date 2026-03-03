package org.blackstamp.sleepychronicles.api.data.days;

import lombok.Getter;
import lombok.Setter;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;

public class DayData {
    @Getter @Setter private Integer day = 0;
    @Getter @Setter private Integer maxDay = 10;
    @Getter @Setter private Long timestamp = System.currentTimeMillis() + ConstantFields.ONE_DAY;
}