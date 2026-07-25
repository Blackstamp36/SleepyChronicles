package org.blackstamp.sleepychronicles.game.mobs.goals.boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttack;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.SleepyProjectiles;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum DarknessEmperorAttacks implements BossAttack {
    HOMING_RAIN{
        private static final Random RANDOM = ThreadLocalRandom.current();
        private static final int COUNT = 8;
        private static final int RADIUS = 25;

        @Override
        public void cast(BossMob boss, LivingEntity target){
            if(!(target instanceof Player)) return;

            boss.setTickCooldown(getCooldownTicks());

            final Vec3 startPos = new Vec3(boss.getX(), boss.getY(), boss.getZ());
            final Level nmsLevel = boss.level();
            final org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();

            bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH,0.85F,1.25F);
            bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,0.85F,1.25F);
            bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                    20,0, false,false));

            for(int i = 0; i < COUNT; i++){
                final int finalI = i;
                double angle = RANDOM.nextDouble() * 2 * Math.PI;
                double distance = RANDOM.nextDouble() * RADIUS;

                double x = startPos.x() + Math.cos(angle) * distance;
                double y = startPos.y() + 30;
                double z = startPos.z() + Math.sin(angle) * distance;

                Vec3 newPos = new Vec3(x, y, z);

                Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () ->
                        SleepyProjectiles.DARK_SPARK.shootHoming(nmsLevel, boss, newPos, target, 40 + (finalI * 10)
                        ), RANDOM.nextInt(5));
            }
        }

        @Override public double getMinDistance(){ return 5.0D; }
        @Override public double getMaxDistance(){ return 10.0D; }
        @Override public int getWindupTicks(){ return 40; }
        @Override public int getRecoveryTicks(){ return 60; }
        @Override public int getCooldownTicks(){ return 60; }
    },

    LEVITATION_SPELL{
        @Override
        public void cast(BossMob boss, LivingEntity target){
            if(!(target instanceof Player)) return;

            boss.setTickCooldown(getCooldownTicks());

            org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
            bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,100,0, false,false));
            bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.25F);

            Level nmsLevel = target.level();
            int castDuration = ThreadLocalRandom.current().nextInt(4,9) * 20;

            bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                    castDuration,0, false,false));

            new BukkitRunnable(){
                int tickCount = 0;

                @Override
                public void run() {
                    tickCount++;

                    if(tickCount >= castDuration) this.cancel();

                    if(tickCount % 20 == 0){
                        SleepyProjectiles.DARK_SPARK.shootLinear(nmsLevel, boss, boss.position(), null);

                        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.85F,1.75F);
                    }
                }
            }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
        }

        @Override public double getMinDistance(){ return 2.5D; }
        @Override public double getMaxDistance(){ return 6.0D; }
        @Override public int getWindupTicks(){ return 40; }
        @Override public int getRecoveryTicks(){ return 40; }
        @Override public int getCooldownTicks(){ return 50; }
    }
}