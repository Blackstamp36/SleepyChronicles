package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ghast.eodPhase2;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class eodSupernovaGoal extends Goal {

    private final int supernovaDamage;
    private final emperorOfDarkness entity;
    private final int tickCooldown;

    public eodSupernovaGoal(emperorOfDarkness entity, int supernovaDamage, int tickCooldown) {
        this.entity = entity;
        this.supernovaDamage = supernovaDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(emperorOfDarkness.phase2Attacks.SUPERNOVA)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
    }

    @Override
    public void stop(){
        entity.currentAttack = emperorOfDarkness.phase2Attacks.HOMING_RAIN;
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();
        if (target == null) return;

        initSuperNovaAttack();
    }

    private void initSuperNovaAttack(){
        Entity bukkitE = entity.getBukkitLivingEntity();
        bukkitE.setGlowing(true);
        entity.setNoAi(true);
        ParticleManager pM = new ParticleManager(bukkitE.getWorld());

        entity.increaseTickCooldown(80);

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                Location currentLoc = bukkitE.getLocation();

                if(tickCount++ >= 80) {
                    entity.setNoAi(false);
                    bukkitE.setGlowing(false);
                    pM.particle(currentLoc, Particle.EXPLOSION_EMITTER, null,
                            25,5.25,5.25,5.25,1.0);
                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(16)){
                        nearby.damage(supernovaDamage, entity.getBukkitLivingEntity());
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.85F,0.75F);
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 1.75F);
                    }

                    this.cancel();
                }

                if(tickCount % 20 == 0) {
                    pM.sphere(currentLoc, Particle.END_ROD,
                            16, 500, 0.0,null);
                    pM.sphere(currentLoc, Particle.SQUID_INK,
                            16, 250, 0.0,null);

                    for (org.bukkit.entity.Player nearby : entity.getBukkitLivingEntity().getLocation().getNearbyPlayers(32))
                        nearby.playSound(nearby.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.85F, 0.75F);
                }

            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);

    }
}
