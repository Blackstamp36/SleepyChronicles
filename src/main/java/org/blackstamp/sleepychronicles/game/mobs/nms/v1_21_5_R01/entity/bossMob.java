package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity;

import net.minecraft.world.entity.Mob;

public interface bossMob {
    void setTickCooldown(int value);
    void decrementTickCooldown();
    void increaseTickCooldown(int value);
    void setBossPhase(int value);
    int getBossPhase();
    int getThemeDurationTicks();
    String getBossTheme();

    Mob getEntity();
}
