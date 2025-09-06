package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.vex.stardustVex;

import java.util.UUID;

public interface allyMob {
    void setSummonerUUID(UUID uuid);
    UUID getSummonerUUID();
    boolean isAllyMob();
    Mob getEntity();
}
