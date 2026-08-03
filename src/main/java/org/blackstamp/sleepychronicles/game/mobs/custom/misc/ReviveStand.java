package org.blackstamp.sleepychronicles.game.mobs.custom.misc;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReviveStand extends ArmorStand {

    private final UUID standUUID;
    private final UUID downedUUID;
    private final int requiredHits;
    private final RunInstance run;

    private int currentHits = 0;

    public ReviveStand(Level level, RunInstance run, UUID downedUUID){
        super(EntityType.ARMOR_STAND, level);

        this.run = run;
        this.standUUID = this.getUUID();
        this.downedUUID = downedUUID;
        this.requiredHits = this.run.getDownedCount(downedUUID) * 8;

        this.run.addReviveStand(downedUUID, standUUID);

        this.registerAttributes();
        this.checkHits(this);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float value){
        super.hurtServer(level,source,value);

        if(!(source.getEntity() instanceof Player attacker)) return false;
        if(PersistentData.has(attacker,SleepyKeys.IS_DOWNED.get())) return false;
        if(!attacker.getUniqueId().equals(downedUUID)) return false;

        this.currentHits++;
        this.checkHits(this);

        return false;
    }

    private void checkHits(LivingEntity entity){
        entity.setCustomName(TextFormatter.toComponent(this.currentHits + "/" + this.requiredHits,"#00000"));

        if(this.currentHits >= this.requiredHits){
            RunManager.revivePlayer(this.downedUUID, this.run);
            this.discard();
        }
    }


    public void spawnParticle(ParticleBuilder builder, int amount){
        builder.location(this.level().getWorld(), this.getX(), this.getY(), this.getZ())
                .count(amount)
                .offset(0.5F,0.25F,0.5F)
                .spawn();
    }

    private void registerAttributes(){
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setCustomNameVisible(true);

        // Revive Stand settings.
        this.setInvisible(true);
        this.setSmall(true);
    }

    @Override
    public boolean isPushable(){ return false; }
    @Override
    public boolean isPickable(){ return false; }
}
