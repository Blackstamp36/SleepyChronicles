package org.blackstamp.sleepychronicles.game.mobs.goals;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossState;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.*;
import java.util.logging.Logger;

public class BossAggroGoal extends Goal {
    private final BossMob boss;
    private final float decay;
    private final int decayTicks;
    private final double aggroRadius;
    private final double maxDistance;
    private Player aggroedPlayer;

    public BossAggroGoal(BossMob boss, float decay, int decayTicks, double aggroRadius, double maxDistance){
        this.boss = boss;
        this.decay = decay;
        this.decayTicks = decayTicks;
        this.aggroRadius = aggroRadius;
        this.maxDistance = maxDistance;

        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        UUID highestAggro = null;
        float highestValue = 0;

        if(boss.getAggroTable().isEmpty()){
            List<Player> nearbyPlayers = boss.level().getEntitiesOfClass(
                    Player.class,  boss.getBoundingBox().inflate(aggroRadius)
                    );

            for(Player p : nearbyPlayers){
                UUID uuid = p.getUUID();

                if(boss.isPlayerValid(p)) boss.getAggroTable().putIfAbsent(uuid, 0.1F);
            }
        }

        for(Iterator<Map.Entry<UUID,Float>> it = boss.getAggroTable().entrySet().iterator(); it.hasNext();){
            Map.Entry<UUID,Float> entry = it.next();
            UUID uuid = entry.getKey();
            float aggroValue = entry.getValue();
            Player p = boss.level().getPlayerByUUID(uuid);

            if(boss.tickCount % decayTicks == 0){
                float decayedValue = (aggroValue * decay);

                if(decayedValue < 0.1){
                    it.remove();
                    continue;
                }

                entry.setValue(decayedValue);
                aggroValue = decayedValue;
            }

            if(!boss.isPlayerValid(p)){
                it.remove();
                continue;
            }

            if(highestValue < aggroValue){
                highestAggro = uuid;
                highestValue = aggroValue;
            }
        }

        if(highestAggro != null){
            this.aggroedPlayer = boss.level().getPlayerByUUID(highestAggro);
            return true;
        }

        this.aggroedPlayer = null;
        boss.setTarget(null);
        boss.setState(BossState.IDLE);
        return false;
    }

    @Override
    public void tick(){
        if(boss.getTarget() != aggroedPlayer) boss.setTarget(aggroedPlayer, EntityTargetEvent.TargetReason.FOLLOW_LEADER);
    }

    @Override
    public void start(){
        boss.setTarget(aggroedPlayer, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
        checkDistance(aggroedPlayer);
    }

    @Override
    public void stop(){
        if(boss.getTarget() == null || !boss.getTarget().isAlive())
            boss.setState(BossState.IDLE);
    }

    private void checkDistance(LivingEntity target){
        BossState state = boss.getState();

        if(state == BossState.ATTACKING
                || state == BossState.WINDING_UP
                || state == BossState.RECOVERING
        ) return;

        if(target == null || !target.isAlive()) return;

        double distance = boss.distanceTo(target);

        if(distance > maxDistance) boss.setState(BossState.APPROACHING);
        else boss.setState(BossState.STALKING);
    }

    private void showAggroTable(HashMap<UUID,Float> table){
        Logger logger = SleepyChronicles.getInstance().getLogger();

        int count = 1;

        logger.warning("---");

        for (Map.Entry<UUID, Float> entry : table.entrySet()) {
            UUID uuid = entry.getKey();
            float damage = entry.getValue();
            Player p = boss.level().getPlayerByUUID(uuid);
            String name = (p != null) ? p.getScoreboardName() : "Unknown";

            logger.warning(count + ". " + name + "(" + damage + ")");
            count++;
        }

        logger.warning("---");
    }
}