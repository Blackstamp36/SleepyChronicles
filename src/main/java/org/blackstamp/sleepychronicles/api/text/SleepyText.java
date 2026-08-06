package org.blackstamp.sleepychronicles.api.text;

import org.blackstamp.sleepychronicles.api.color.SleepyPalette;

public record SleepyText(
        String text,
        SleepyPalette palette,
        int colorType
){}
