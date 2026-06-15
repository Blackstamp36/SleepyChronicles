package org.blackstamp.sleepychronicles.api.text;

public class TextFormatter {

    public static String toIDString(String value){
        return value.toLowerCase().replaceAll("[^a-z0-9_]","_");
    }
}
