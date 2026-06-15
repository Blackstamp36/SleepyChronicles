package org.blackstamp.sleepychronicles.game.mobs.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttack;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossState;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BossAttackGoal extends Goal {
    private final BossMob boss;
    private final Mob entity;

    public BossAttackGoal(BossMob boss){
        this.boss = boss;
        this.entity = boss;

        this.setFlags(EnumSet.of(Flag.MOVE,Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        return entity.getTarget() != null;
    }

    @Override
    public void tick(){
        final LivingEntity target = boss.getTarget();

        if(target == null) return;

        BossAttack attack = boss.getQueuedAttack();

        switch(boss.getState()){
            case APPROACHING -> {
                if(!(boss.getTickCooldown() <= 0)) return;

                List<BossAttack> validAttacks = new ArrayList<>();
                double distance = entity.distanceTo(target);
                double minDistance = attack.getMinDistance();
                double maxDistance = attack.getMaxDistance();

                for(BossAttack valid : boss.getAttacks())
                    if(distance >= minDistance && distance <= maxDistance) validAttacks.add(valid);

                if(!validAttacks.isEmpty()){
                    BossAttack chosenAttack = validAttacks.get(boss.getRandom().nextInt(validAttacks.size()));
                    boss.setQueuedAttack(chosenAttack);
                    boss.setState(BossState.WINDING_UP);

                }else{
                    if(distance > maxDistance) boss.setState(BossState.APPROACHING);
                    else boss.setState(BossState.STALKING);
                }
            }

            case WINDING_UP -> {
                int windupTicks = attack.getWindupTicks();

                if(boss.getStateTicks() >= windupTicks) boss.setState(BossState.ATTACKING);
            }

            case ATTACKING -> {
                BossAttack queuedAttack = boss.getQueuedAttack();
                queuedAttack.cast(boss,target);
                boss.setTickCooldown(queuedAttack.getCooldownTicks());
                boss.setState(BossState.RECOVERING);
            }

            case RECOVERING -> {
                int recoveryTicks = attack.getRecoveryTicks();

                if(boss.getStateTicks() >= recoveryTicks) boss.setState(BossState.APPROACHING);
            }
        }
    }
}