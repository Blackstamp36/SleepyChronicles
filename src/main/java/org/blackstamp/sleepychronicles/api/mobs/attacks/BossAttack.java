package org.blackstamp.sleepychronicles.api.mobs.attacks;

import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public interface BossAttack extends SleepyAttack<BossMob> {
    void onWindupStart(BossMob boss);
    void onWindupTick(BossMob boss, int windupTick);
    int getWindupTicks();
    int getRecoveryTicks();
    int getWeight();
}