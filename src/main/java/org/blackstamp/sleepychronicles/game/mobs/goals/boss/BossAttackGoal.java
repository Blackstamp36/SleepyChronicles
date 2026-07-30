package org.blackstamp.sleepychronicles.game.mobs.goals.boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.api.mobs.attacks.BossAttack;
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

        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse(){
        if(boss.getTarget() == null) return false;

        return boss.getState() == BossState.WINDING_UP
                || boss.getState() == BossState.ATTACKING
                || boss.getState() == BossState.STALKING
                || boss.getState() == BossState.APPROACHING
                || boss.getState() == BossState.RECOVERING;
    }

    @Override
    public void tick(){
        final LivingEntity target = boss.getTarget();

        if(target == null) return;

        BossState state = boss.getState();

        if(state == BossState.APPROACHING || state == BossState.STALKING){
            if (!(boss.getTickCooldown() <= 0)) return;

            List<BossAttack> validAttacks = new ArrayList<>();
            double distance = entity.distanceTo(target);

            for (BossAttack valid : boss.getBossAttacks()) // Add all valid attacks to a list.
                if(distance >= valid.getMinDistance() && distance <= valid.getMaxDistance())
                    validAttacks.add(valid);

            if(!validAttacks.isEmpty()){
                BossAttack previous = boss.getPreviousAttack();

                if(validAttacks.size() > 1 && previous != null) validAttacks.remove(previous);

                int totalWeight = 0;
                for(BossAttack attack : validAttacks){ totalWeight += attack.getWeight(); }

                int randomTicket = boss.getRandom().nextInt(totalWeight);
                int currentWeight = 0;

                BossAttack chosenAttack = validAttacks.getFirst(); // We add our fallback, just for it not to be null.

                for(BossAttack attack : validAttacks){
                    currentWeight += attack.getWeight();
                    if(randomTicket < currentWeight){
                        chosenAttack = attack;
                        break;
                    }
                }

                boss.setPreviousAttack(chosenAttack);
                boss.setQueuedAttack(chosenAttack);
                boss.setState(BossState.WINDING_UP);
                chosenAttack.onWindupStart(boss);
            }
            return;
        }

        BossAttack attack = boss.getQueuedAttack(); // Now that we have our attack properly queued, we can get its data.
        if(attack == null){
            boss.setState(BossState.IDLE);
            return;
        }

        switch(state){
            case BossState.WINDING_UP -> {
                if(boss.getStateTicks() >= attack.getWindupTicks()){ boss.setState(BossState.ATTACKING); }

                attack.onWindupTick(boss, boss.getStateTicks());
            }

            case BossState.ATTACKING -> {
                attack.cast(boss, boss.getTarget());

                boss.setState(BossState.RECOVERING);
            }

            case BossState.RECOVERING -> {
                if(boss.getStateTicks() >= attack.getRecoveryTicks()){
                    boss.setState(BossState.IDLE);
                    boss.setQueuedAttack(null);
                }
            }
        }
    }

    @Override
    public void stop(){
        if(boss.getTarget() == null || !boss.getTarget().isAlive()) {
            boss.setState(BossState.IDLE);
            boss.setQueuedAttack(null);
        }
    }
}