package org.blackstamp.sleepychronicles.game.mobs.custom.misc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.ReviveManager;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReviveStand extends ArmorStand {

    // Revive Stand vars.
    private final RunInstance run;
    private final UUID downedUUID;
    private final double maxHealthPool;
    private double currentReviveHealth;
    private final ParticleManager particle;
    private Location location = null;

    public ReviveStand(Level level, RunInstance run, UUID downedUUID){
        super(EntityType.ARMOR_STAND, level);

        Player downedPlayer = Bukkit.getPlayer(downedUUID);
        double downedMaxHealth = downedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue();

        this.run = run;
        this.downedUUID = downedUUID;
        this.maxHealthPool = (downedMaxHealth * 1.36) * run.getDownedCount(downedUUID);
        this.currentReviveHealth = this.maxHealthPool;
        this.particle = new ParticleManager(level.getWorld());

        this.registerAttributes();
        this.checkHealth(this);
    }

    @Override
    public void tick(){
        super.tick();

        if(tickCount % 15 == 0){
            this.setYRot(this.getYRot() + 30.0F);

            if(this.location == null){ this.location = new Location(this.level().getWorld(), this.getX(), this.getY(), this.getZ()); }

            this.particle.particle(this.location, Particle.CHERRY_LEAVES, null, 25,1.0D,1.0D);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount){
        if(!(source.getDirectEntity() instanceof ServerPlayer attacker)) return false;

        UUID attackerUUID = attacker.getUUID();
        Player attackerBukkit = Bukkit.getPlayer(attackerUUID);

        if(PersistentData.has(attackerBukkit,SleepyKeys.IS_DOWNED.get())) return false;
        if(attackerUUID.equals(downedUUID)) return false;

        level.playSound(
                null, this.blockPosition(),
                SoundEvents.HONEY_BLOCK_BREAK, SoundSource.HOSTILE,
                0.5F,0.85F
        );
        level.playSound(
                null, this.blockPosition(),
                SoundEvents.CONDUIT_DEACTIVATE, SoundSource.HOSTILE,
                0.35F,0.75F
        );
        this.currentReviveHealth -= amount;
        this.checkHealth(this);

        return false;
    }

    private void checkHealth(LivingEntity entity){
        String healthText = String.format("%.1f",this.currentReviveHealth) + "/" + String.format("%.1f",this.maxHealthPool);

        entity.setCustomName(
                TextFormatter.toComponent(healthText, SleepyPalette.SLEEPY)
        );

        if(this.currentReviveHealth <= 0){
            ReviveManager.revivePlayer(this.downedUUID, this.run);
            this.discard();
        }
    }

    private void registerAttributes(){
        this.setSilent(true);
        this.setInvisible(true);
        this.setNoGravity(true);
        this.setCustomNameVisible(true);

        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHERRY_LEAVES));
    }

    @Override
    public boolean isPushable(){ return false; }
    @Override
    public boolean isPickable(){ return true; }
}
