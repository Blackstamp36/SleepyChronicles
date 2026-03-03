package org.blackstamp.sleepychronicles.game.items;

import lombok.Getter;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;

@Getter
public enum ItemFamily{
    TRINKETS("trinkets",SleepyPalette.TRINKET),

    PALE_TOOLS("pale",SleepyPalette.PALE),

    SOLAR_SET("solar", SleepyPalette.SOLAR),
    VORTEX_SET("vortex", SleepyPalette.VORTEX),
    STARDUST_SET("stardust", SleepyPalette.STARDUST);

    private final SleepyPalette palette;
    private final String name;

    ItemFamily(String name, SleepyPalette palette){
        this.name = name;
        this.palette = palette;
    }

}
