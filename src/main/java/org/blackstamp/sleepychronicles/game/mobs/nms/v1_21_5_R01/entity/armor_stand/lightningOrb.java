package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class lightningOrb extends ArmorStand implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    private final LivingEntity caster;
    private int lifetimeTicks;
    private int tickCount;
    private final int lightningDamage = 36;
    private final int particleCount = 32;

    public lightningOrb(EntityType<? extends ArmorStand> entityType, Level level,
                        int lifetimeTicks, LivingEntity caster) {
        super(entityType, level);

        this.lifetimeTicks = lifetimeTicks;
        this.caster = caster;

        registerAttributes();
    }

    public void registerAttributes() {
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#20d6c7") + "Lightning Orb"));

        this.setInvulnerable(true);
        this.setSilent(true);
        this.setNoGravity(true);
        this.setShowArms(false);
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        if(tickCount % 10 == 0) shockNearbyPlayers();

        if(tickCount >= lifetimeTicks) this.discard();
    }

    private void shockNearbyPlayers(){
        for(Player n : bukkitE.getLocation().getNearbyPlayers(4,1,4)){
            Level nmsLevel = this.level();
            ParticleManager pM = new ParticleManager(bukkitE.getWorld());
            Location playerL = n.getLocation();

            pM.particle(playerL, Particle.ELECTRIC_SPARK, null,
                    particleCount, 0.5,0.25,0.5,0.0);
            pM.particle(playerL, Particle.ENCHANTED_HIT, null,
                    particleCount, 0.5,0.25,0.5,0.0);

            n.playSound(playerL, Sound.ITEM_TRIDENT_THUNDER, 0.45F, 0.75F);

            n.damage(lightningDamage, caster.getBukkitLivingEntity());
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, nmsLevel);
            nmsLevel.addFreshEntity(bolt);
            bolt.setPos(n.getX(), n.getY(), n.getZ());
        }
    }

    @Override
    public void handleImpact(LivingEntity damagedEntity) {
    }
}
