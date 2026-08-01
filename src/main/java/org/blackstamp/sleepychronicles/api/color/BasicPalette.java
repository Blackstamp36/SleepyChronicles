package org.blackstamp.sleepychronicles.api.color;

public enum BasicPalette {
    BLACK("black"),
    DARK_RED("dark_red"),
    DARK_BLUE("dark_blue"),
    DARK_GREEN("dark_green"),
    DARK_GRAY("dark_gray"),
    DARK_PURPLE("dark_purple"),

    GOLD("gold"),
    CYAN("cyan"),
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    GRAY("gray"),
    MAGENTA("magenta"),
    YELLOW("yellow"),
    AQUA("aqua"),
    WHITE("white");

    private final String color;

    BasicPalette(String color){
        this.color = color;
    }

    public String getColor(){ return "<" + color + ">"; }
}
