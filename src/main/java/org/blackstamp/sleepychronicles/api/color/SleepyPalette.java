package org.blackstamp.sleepychronicles.api.color;

import net.kyori.adventure.text.format.TextColor;
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

    SLEEPY("#9381ff","#b8b8ff","#f8f7ff"),

    // Player messengers.
    STAFF("#c9cba3","#ffe1a8","#e26d5c"),
    ERROR("#dc143c"),
    BROADCAST("#83c9f4");

    private final TextColor primary;
    private final TextColor secondary;
    private final TextColor tertiary;

    SleepyPalette(String primary){ this(primary, primary, primary); }
    SleepyPalette(String primary, String secondary){ this(primary, secondary, primary); }
    SleepyPalette(String primary, String secondary, String tertiary){
        this.primary = TextColor.fromCSSHexString(primary);
        this.secondary = TextColor.fromCSSHexString(secondary);
        this.tertiary = TextColor.fromCSSHexString(tertiary);
    }

    public @NotNull TextColor getColor(){ return this.primary; }
    public @NotNull TextColor getColor(int type){
        return switch(type){
            case 1 -> this.secondary;
            case 2 -> this.tertiary;
            default -> this.primary;
            };
        }
    }