package org.blackstamp.sleepychronicles.game.mobs.goals;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;

import java.util.*;

public class BossAggroGoal extends Goal {
    // todo: 1. check for errors in goals. like attack or dodge.
    // todo: 2. view for possible errors in the 'BossMob' class.

    private final BossMob boss;
    private final float decay;
    private final int decayTicks;
    private Player aggroPlayer;

    public BossAggroGoal(BossMob boss, float decay, int decayTicks){
        this.boss = boss;
        this.decay = decay;
        this.decayTicks = decayTicks;

        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        HashMap<UUID,Float> table = boss.getAggroTable();

        if(table.isEmpty()) return false;

        UUID highestAggro = null;
        float highestValue = 0;

        for(Iterator<Map.Entry<UUID,Float>> it = table.entrySet().iterator(); it.hasNext();){
            Map.Entry<UUID,Float> entry = it.next();
            UUID uuid = entry.getKey();
            float aggroValue = entry.getValue();
            Player p = boss.level().getPlayerByUUID(uuid);

            if(boss.tickCount % decayTicks == 0){
                float decayedValue = aggroValue * decay;
                entry.setValue(decayedValue);
                aggroValue = decayedValue;
            }

            if(!isValid(p)){
                it.remove();
                continue;
            }

            if(highestValue < aggroValue){
                highestAggro = uuid;
                highestValue = aggroValue;
            }
        }

        if(highestAggro != null){
            this.aggroPlayer = boss.level().getPlayerByUUID(highestAggro);
            return true;
        }

        return false;
    }

    @Override
    public void start(){ boss.setTarget(aggroPlayer); }

    private boolean isValid(Player p){
        if(p == null) return false;
        if(!(p instanceof ServerPlayer serverPlayer)) return false;
        if(!serverPlayer.gameMode().equals(GameType.SURVIVAL)) return false;
        if(!serverPlayer.isAlive()) return false;

        ResourceKey<Level> dimension = p.level().dimension();
        ResourceKey<Level> bossDimension = boss.level().dimension();

        return dimension == bossDimension;
    }
}