package org.blackstamp.sleepychronicles.game.mobs.custom.vanilla;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.SleepyEntity;
import org.blackstamp.sleepychronicles.api.mobs.config.BaseConfig;

public class VanillaZombie extends Zombie implements SleepyEntity {

    BaseConfig config;

    public VanillaZombie(Level world, BaseConfig config) {
        super(EntityType.ZOMBIE,world);
        this.config = config;

        this.applyData(this);
    }

    @Override
    public BaseConfig getConfig(){ return this.config; }
}
