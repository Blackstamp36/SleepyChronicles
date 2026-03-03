package org.blackstamp.sleepychronicles.game.mobs.custom.bosses;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttacks;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossWrapperAware;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor.DarknessEmperorAttacks;
import org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor.DarknessEmperorGoal;

public class DarknessEmperor extends BossMob {
    public DarknessEmperor(Level level, String name, String color){
        super(new DarknessEmperorEntity(level), level, name, color);
    }

    @Override
    public BossAttacks getNextAttack(){
        return DarknessEmperorAttacks.HOMING_RAIN;
    }

    private static class DarknessEmperorEntity extends Ghast implements BossWrapperAware {

        private BossMob wrapper;

        public DarknessEmperorEntity(Level level){
            super(EntityType.GHAST, level);
        }

        public void registerGoals(){
            super.goalSelector.addGoal(0,new DarknessEmperorGoal(wrapper));
        }

        @Override
        public void setWrapper(BossMob wrapper){ this.wrapper = wrapper; }

        @Override
        public void tick(){
            if(wrapper.getTickCooldown() >= 1) wrapper.setTickCooldown(wrapper.getTickCooldown() - 1);
        }
    }
}