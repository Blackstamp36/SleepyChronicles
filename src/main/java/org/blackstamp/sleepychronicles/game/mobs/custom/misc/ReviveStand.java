package org.blackstamp.sleepychronicles.game.mobs.custom.misc;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.ReviveManager;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReviveStand extends ArmorStand {

    private final RunInstance run;
    private final UUID downedUUID;
    private final double maxHealthPool;
    private double currentReviveHealth;

    private int currentHits = 0;

    public ReviveStand(Level level, RunInstance run, UUID downedUUID){
        super(EntityType.ARMOR_STAND, level);

        Player downedPlayer = Bukkit.getPlayer(downedUUID);
        double downedMaxHealth = downedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue();

        this.run = run;
        this.downedUUID = downedUUID;
        this.maxHealthPool = (downedMaxHealth * 0.75) * run.getDownedCount(downedUUID);
        this.currentReviveHealth = this.maxHealthPool;

        this.registerAttributes();
        this.checkHealth(this);
    }

    @Override
    public void tick(){
        super.tick();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float value){
        super.hurtServer(level,source,value);

        if(!(source.getEntity() instanceof Player attacker)) return false;
        if(PersistentData.has(attacker,SleepyKeys.IS_DOWNED.get())) return false;
        if(attacker.getUniqueId().equals(downedUUID)) return false;

        this.currentReviveHealth -= value;
        this.checkHealth(this);

        return false;
    }

    private void checkHealth(LivingEntity entity){
        entity.setCustomName(TextFormatter.toComponent(this.currentReviveHealth + "/" + this.maxHealthPool, "#b8b8ff"));

        if(this.currentReviveHealth <= 0){
            ReviveManager.revivePlayer(this.downedUUID, this.run);
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
        this.setSilent(true);

        // Revive Stand settings.
        this.setInvisible(true);
        this.setNoGravity(true);
        this.setCustomNameVisible(true);
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHERRY_LEAVES));
    }

    @Override
    public boolean isPushable(){ return false; }
    @Override
    public boolean isPickable(){ return false; }
}
