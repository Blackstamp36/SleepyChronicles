package org.blackstamp.sleepychronicles.api.sound;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

public enum SoundRegistry {
    OPEN_1(new SoundConfig(
            "ui.cartography_table.take_result",
            Sound.Source.MASTER,
            1.0F,
            1.0F
    ));

    @Getter private final Sound sound;

    SoundRegistry(SoundConfig soundConfig) {
        this.sound = Sound.sound(
                Key.key(soundConfig.soundID()),
                soundConfig.soundSource(),
                soundConfig.volumeValue(),
                soundConfig.pitchValue()
        );
    }
}
