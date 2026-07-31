package org.blackstamp.sleepychronicles.api.mobs.attacks;

import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public interface BossAttack extends SleepyAttack<BossMob> {
    default void onWindupStart(BossMob boss){}
    default void onWindupTick(BossMob boss, int windupTick){}
    int getWindupTicks();
    int getRecoveryTicks();
    int getWeight();
}