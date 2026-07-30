package org.blackstamp.sleepychronicles.game.mobs.goals.sleepy_mobs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;

public class GenericSkillGoal extends Goal {

    Mob mob;
    SleepyAttack<Mob> attack;
    int currentCooldown = 0;

    public GenericSkillGoal(Mob mob, SleepyAttack<Mob> attack){
        this.mob = mob;
        this.attack = attack;
    }

    @Override
    public boolean canUse(){
        return this.mob.getTarget() != null;
    }

    @Override
    public void tick(){
        super.tick();

        if(currentCooldown > 0) currentCooldown--;

        LivingEntity target = this.mob.getTarget();
        if(target == null) return;

        double distance = this.mob.distanceTo(target);

        if((distance >= attack.getMinDistance() && distance <= attack.getMaxDistance()) && currentCooldown <= 0){
            this.attack.cast(this.mob, target);
            currentCooldown = this.attack.getCooldownTicks();
        }
    }
}
