package org.blackstamp.sleepychronicles.api.color;

import co.aikar.commands.annotation.Optional;
import org.jetbrains.annotations.NotNull;

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
    STARDUST("#64c7e8"),

    SLEEPY("#9381ff","#b8b8ff","#f8f7ff"),
    TOTEM("#ced45b"),
    STAFF("#c9cba3","#ffe1a8","#e26d5c"),
    NOTIFICATION("#83c9f4");

    private final String color1;
    private final String color2;
    private final String color3;

    SleepyPalette(String color){ this(color, color, color); }
    SleepyPalette(String color1, String color2){ this(color1, color2, color1); }
    SleepyPalette(String color1, String color2, String color3){
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public String getMiniColor1(){ return setDiamonds(this.color1); }
    public String getMiniColor2(){ return setDiamonds(this.color2); }
    public String getMiniColor3(){ return setDiamonds(this.color3); }

    public @NotNull String color(@Optional int index, boolean brackets){
        String colorToGet;

        switch(index){
            case 1 -> colorToGet = this.color2;
            case 2 -> colorToGet = this.color3;
            default -> colorToGet = this.color1;
        }

        if(brackets) colorToGet = setDiamonds(colorToGet);

        return colorToGet;
        }

    private static String setDiamonds(String color){ return "<" + color + ">"; }
    }