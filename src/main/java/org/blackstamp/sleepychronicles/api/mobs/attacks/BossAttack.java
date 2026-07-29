package org.blackstamp.sleepychronicles.api.mobs.attacks;

import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public interface BossAttack extends SleepyAttack<BossMob> {
    int getWindupTicks();
    int getRecoveryTicks();
    int getWeight();
}