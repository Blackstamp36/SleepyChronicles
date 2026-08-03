package org.blackstamp.sleepychronicles.game.mobs.custom.misc;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ReviveStand extends ArmorStand {

    private static final PotionEffectType[] downedEffectTypes = {
            PotionEffectType.SLOWNESS,
            PotionEffectType.DARKNESS,
            PotionEffectType.GLOWING
    };

    private final UUID downedUUID;
    private final int requiredHits;

    private int currentHits = 0;

    public ReviveStand(Level level, UUID downedUUID, int downedCount){
        super(EntityType.ARMOR_STAND, level);

        this.downedUUID = downedUUID;
        this.requiredHits = downedCount * 8;

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

    private void revivePlayer(UUID uuid){ // Execute revive logic..
        Player p = Bukkit.getPlayer(uuid);

        if(p == null || !p.isOnline()) return;

        PersistentData.remove(p, SleepyKeys.IS_DOWNED.get());
        PlayerManager.clearPots(p,downedEffectTypes);

        p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getBaseValue() * 0.3);
        p.setPose(Pose.STANDING);
        ChatManager.sendWarning(p,"You've been revived!",null);
    }

    private void checkHits(LivingEntity entity){
        entity.setCustomName(TextFormatter.toComponent(this.currentHits + "/" + this.requiredHits,"#00000"));

        if(this.currentHits >= this.requiredHits){
            this.revivePlayer(this.downedUUID);
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

        // Revive Stand settings.
        this.setInvisible(true);
        this.setSmall(true);
    }

    @Override
    public boolean isPushable(){ return false; }
    @Override
    public boolean isPickable(){ return false; }
}
