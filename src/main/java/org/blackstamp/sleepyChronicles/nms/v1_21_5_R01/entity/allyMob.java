package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity;

import java.util.UUID;

public interface allyMob {
    void setSummonerUUID(UUID uuid);
    UUID getSummonerUUID();
    boolean isAllyMob();
}
