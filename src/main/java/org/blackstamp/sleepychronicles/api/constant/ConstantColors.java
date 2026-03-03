package org.blackstamp.sleepychronicles.api.constant;

public final class ConstantColors {

    public static final String BLACK = brackets("black");
    public static final String DARK_RED = brackets("dark_red");
    public static final String DARK_BLUE = brackets("dark_blue");
    public static final String DARK_GREEN = brackets("dark_green");
    public static final String DARK_GRAY = brackets("dark_gray");
    public static final String DARK_PURPLE = brackets("dark_purple");
    public static final String GOLD = brackets("gold");
    public static final String CYAN = brackets("cyan");

    public static final String RED = brackets("red");
    public static final String BLUE = brackets("blue");
    public static final String GREEN = brackets("green");
    public static final String GRAY = brackets("gray");
    public static final String MAGENTA = brackets("magenta");
    public static final String YELLOW = brackets("yellow");
    public static final String AQUA = brackets("aqua");
    public static final String WHITE = brackets("white");

    public static final String SLEEPY = brackets("#6932a8");
    public static final String TOTEM = brackets("#ced45b");
    public static final String STAFF = brackets("#32a85e");

    private static String brackets(String color){ return "<" + color + ">"; }
}