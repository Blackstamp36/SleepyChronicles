package org.blackstamp.sleepychronicles.game.mobs.custom.bosses;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttacks;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossWrapperAware;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor.DarknessEmperorAttacks;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAttackGoal;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DarknessEmperor extends BossMob {
    private final static Random RANDOM = ThreadLocalRandom.current();
    private final static String NAME = "Darkness Emperor";
    private final static String COLOR = "#5e17a1";
    private final static int MAX_HEALTH = 1000;

    public DarknessEmperor(Level level){
        super(new DarknessEmperorEntity(level), level, NAME, COLOR);
    }

    @Override
    public void registerAttributes(){
        this.setMaxHealth(MAX_HEALTH);
        this.setSilent(true);
    }

    @Override
    public BossAttacks getNextAttack(){
        final int attack = RANDOM.nextInt(DarknessEmperorAttacks.values().length);

        return DarknessEmperorAttacks.values()[attack];
    }

    private static class DarknessEmperorEntity extends Ghast implements BossWrapperAware {

        private BossMob wrapper;

        public DarknessEmperorEntity(Level level){
            super(EntityType.GHAST, level);
        }

        public void registerGoals(){
            super.goalSelector.addGoal(0,new BossAttackGoal(wrapper));
        }

        @Override
        public void setWrapper(BossMob wrapper){ this.wrapper = wrapper; }

        @Override
        public void tick(){
            if(wrapper.getTickCooldown() >= 1) wrapper.setTickCooldown(wrapper.getTickCooldown() - 1);
        }
    }
}