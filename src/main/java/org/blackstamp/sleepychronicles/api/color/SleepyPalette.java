package org.blackstamp.sleepychronicles.api.color;

import org.jetbrains.annotations.NotNull;

public enum SleepyPalette {
    // Primary, or vanilla-like colors.
    VANILLA("#ebebeb"),
    TRINKET("#d62411"),
    MISCELLANEOUS("#659a7e"),
    TOTEM("#ced45b"),

    // Custom.
    NULL("#db1fdb"),
    BOB("#ada19f"),
    KITSUNE("#e4ced1"),
    SEED_GHOST("#a16e45"),
    PALE("#cfc4c3"),

    // Armor related.
    SOLAR("#cc9933"),
    VORTEX("#4dcbcb"),
    STARDUST("#64c7e8"),

    DARKNESS("#9d78bc","#7e39bf","#5e17a1"),

    SLEEPY("#9381ff","#b8b8ff","#f8f7ff"),

    // Player messengers.
    STAFF("#c9cba3","#ffe1a8","#e26d5c"),
    ERROR("#dc143c"),
    BROADCAST("#83c9f4");

    private final String primary;
    private final String secondary;
    private final String tertiary;

    SleepyPalette(String primary){ this(primary, primary, primary); }
    SleepyPalette(String primary, String secondary, String tertiary){
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
    }

    public @NotNull String getHex(){ return this.primary; }
    public @NotNull String getHex(int colorType){
        return switch(colorType){
            case 1 -> this.secondary;
            case 2 -> this.tertiary;
            default -> this.primary;
            };
        }
    }