package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.api.mobs.projectile.CustomProjectile;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class bigFireball extends ArmorStand implements CustomProjectile {
    private org.bukkit.entity.LivingEntity bukkitE = this.getBukkitLivingEntity();
    Location targetPrevLoc;
    private LivingEntity shooter;
    private int lifetimeTicks;
    private int tickCount;
    private int projectileDamage;

    public bigFireball(EntityType<? extends ArmorStand> entityType, Level level,
                       int projectileDamage, int lifetimeTicks, LivingEntity target, LivingEntity shooter) {
        super(entityType, level);
        this.lifetimeTicks = lifetimeTicks;
        this.shooter = shooter;
        this.targetPrevLoc = target.getBukkitLivingEntity().getLocation();
        this.projectileDamage = projectileDamage;

        registerAttributes();
    }

    public void registerAttributes(){
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.getAttribute(Attributes.SCALE).setBaseValue(2.5F);
    }

    @Override
    public void tick() {
        super.tick();
        Location l = this.getBukkitLivingEntity().getLocation();
        super.tick();
        tickCount++;

        ParticleManager pM = new ParticleManager(bukkitE.getWorld());
        Location projectileLoc = bukkitE.getLocation();

        pM.sphere(projectileLoc, Particle.FLAME, 1,25,0.0,null);
        pM.particle(projectileLoc, Particle.SQUID_INK, null,
                10,0,0,0,0.0);

        if(tickCount % 10 == 0){
            for(Player n : l.getNearbyPlayers(5))
                n.playSound(l, Sound.BLOCK_CONDUIT_DEACTIVATE,0.85F,0.75F);
        }

        if(tickCount >= lifetimeTicks) {
            pM.particle(projectileLoc, Particle.EXPLOSION_EMITTER, null,
                    1,0.05,0.05,0.05,0.5);
            handleFireballDamage();
            this.discard();
            return;
        }

            Vector toTarget = targetPrevLoc.toVector().subtract(projectileLoc.toVector()).normalize();
            Vector currentVel = bukkitE.getVelocity();
            Vector newVel = currentVel.add(toTarget.multiply(0.2)).normalize().multiply(0.2);

            if (newVel.length() < 0.001) return;

            bukkitE.setVelocity(newVel);

            }

    private void handleFireballDamage(){
        for(Entity n : this.getBukkitLivingEntity().getLocation().getNearbyEntities(5,1,5)){
            if(!(n instanceof org.bukkit.entity.LivingEntity bukkitDE)) continue;
            if(n instanceof bossMob) continue;

            bukkitDE.damage(projectileDamage, shooter.getBukkitLivingEntity());
            bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,2));
            bukkitDE.addPotionEffect(new PotionEffect(PotionEffectType.WIND_CHARGED,100,0));

            if(n instanceof Player p) p.playSound(n.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.85F,0.75F);
        }
    }

    public void handleImpact(LivingEntity damagedEntity){
    }

}
