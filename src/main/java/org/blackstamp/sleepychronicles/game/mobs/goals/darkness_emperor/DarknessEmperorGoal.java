package org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttacks;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

public class DarknessEmperorGoal extends Goal {
    BossAttacks attack;
    BossMob boss;

    public DarknessEmperorGoal(BossMob boss){ this.boss = boss; }

    @Override
    public boolean canUse(){
        if(!(boss.getTickCooldown() <= 0)) return false;

        attack = boss.getNextAttack();

        return true;
    }

    @Override
    public void tick(){
        final LivingEntity target = boss.getEntity().getTarget();

        if(target == null) return;

        attack.cast(boss, target);
    }
}
