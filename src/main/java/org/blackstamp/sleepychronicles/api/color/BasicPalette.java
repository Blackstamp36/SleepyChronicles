package org.blackstamp.sleepychronicles.api.color;

public enum BasicPalette {
    BLACK,
    DARK_RED,
    DARK_BLUE,
    DARK_GREEN,
    DARK_GRAY,
    DARK_PURPLE,

    GOLD,
    CYAN,
    RED,
    BLUE,
    GREEN,
    GRAY,
    MAGENTA,
    YELLOW,
    AQUA,
    WHITE;

    private final String color;

    BasicPalette(){ this.color = this.name().toLowerCase(); }

    public String tag(boolean diamonds){
        String colorToGet = this.color;

        if(diamonds) colorToGet = "<" + colorToGet + ">";

        return colorToGet;
    }
}
