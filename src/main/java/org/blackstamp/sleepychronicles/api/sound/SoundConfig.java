package org.blackstamp.sleepychronicles.api.sound;

import net.kyori.adventure.sound.Sound;

public record SoundConfig(
        String soundID,
        Sound.Source soundSource,
        float volumeValue,
        float pitchValue
){}
