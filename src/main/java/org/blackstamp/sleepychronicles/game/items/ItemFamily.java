package org.blackstamp.sleepychronicles.game.items;

import lombok.Getter;
import org.blackstamp.sleepychronicles.api.SleepyPalette;

@Getter
public enum ItemFamily{
    TRINKETS(SleepyPalette.TRINKET),

    PALE_TOOLS(SleepyPalette.PALE),

    SOLAR_SET(SleepyPalette.SOLAR),
    VORTEX_SET(SleepyPalette.VORTEX),
    STARDUST_SET(SleepyPalette.STARDUST);

    private final SleepyPalette palette;

    ItemFamily(SleepyPalette palette){
        this.palette = palette;
    }

}
