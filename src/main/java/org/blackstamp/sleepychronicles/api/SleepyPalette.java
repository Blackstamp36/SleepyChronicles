package org.blackstamp.sleepychronicles.api;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum SleepyPalette {
    VANILLA("#ebebeb"),
    TRINKET("#d62411"),
    MISCELLANEOUS("#659a7e"),

    NULL("#db1fdb"),
    BOB("#ada19f"),
    KITSUNE("#e4ced1"),
    SEED_GHOST("#a16e45"),
    PALE("#cfc4c3"),

    SOLAR("#cc9933"),
    VORTEX("#4dcbcb"),
    STARDUST("#64c7e8");

    private final String color1;
    private final String color2;
    private final String color3;

    SleepyPalette(String color1, String color2, String color3){
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        }

    SleepyPalette(String color){
        this(color, color, color);
    }

    public @NotNull String shift(int index){
        switch(index){
            case 1 -> { return "<" + color2 + ">"; }
            case 2 -> { return "<" + color3 + ">"; }
            }

        return "<" + color1 + ">";
        }
    }


