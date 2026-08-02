package org.blackstamp.sleepychronicles.game.mobs.custom.misc;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.ProjectileSettings;
import org.blackstamp.sleepychronicles.global.utils.manager.CollisionManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ReviveStand extends ArmorStand {
    private final UUID downedUUID;

    public ReviveStand(Level level, UUID downedUUID){
        super(EntityType.ARMOR_STAND, level);

        this.downedUUID = downedUUID;

        // Set the NBT data..
        // PersistentData.set(this,);

        this.registerAttributes();
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

        // Revive settings.
        this.setInvisible(true);
        this.setSmall(true);
        this.setMarker(true);
    }

    @Override
    public boolean isPushable(){ return false; }

    @Override
    public boolean isPickable(){ return false; }
}
