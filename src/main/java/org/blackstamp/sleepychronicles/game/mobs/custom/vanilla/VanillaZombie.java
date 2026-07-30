package org.blackstamp.sleepychronicles.game.mobs.custom.vanilla;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
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

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        if(this.config.drops() != null){
            for(ItemStack item : this.config.drops()){
                if(item == null) continue;

                this.spawnAtLocation(level, item);
            }
        }else{ super.dropCustomDeathLoot(level, damageSource, recentlyHit); }
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource source, boolean hitByAPlayer){
        if(this.config.drops() != null && !this.config.drops().isEmpty()){ return; }

        super.dropFromLootTable(level,source,hitByAPlayer);
    }
}
