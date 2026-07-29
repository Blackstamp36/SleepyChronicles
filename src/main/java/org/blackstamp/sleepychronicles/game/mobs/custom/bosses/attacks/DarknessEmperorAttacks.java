package org.blackstamp.sleepychronicles.game.mobs.custom.bosses.attacks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.attacks.BossAttack;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.SleepyProjectiles;
import org.bukkit.Bukkit;
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

            final Vec3 startPos = new Vec3(boss.getX(), boss.getY(), boss.getZ());
            final Level nmsLevel = boss.level();

            boss.setTickCooldown(getCooldownTicks());

            nmsLevel.playSound(
                    null, boss.blockPosition(),
                    SoundEvents.ELDER_GUARDIAN_DEATH, SoundSource.HOSTILE,
                    1F,1.25F
            );
            nmsLevel.playSound(
                    null, boss.blockPosition(),
                    SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE,
                    1F,0.5F
            );

            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20,0,false,false));

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
        @Override public int getWeight(){ return 2; }
    },

    LEVITATION_SPELL{
        @Override
        public void cast(BossMob boss, LivingEntity target){
            if(!(target instanceof Player)) return;

            Level nmsLevel = target.level();
            int castDuration = ThreadLocalRandom.current().nextInt(4,9) * 15;

            target.addEffect(new MobEffectInstance(MobEffects.LEVITATION,castDuration,0,false,false));
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS,castDuration,0,false,false));

            boss.setTickCooldown(getCooldownTicks());

            nmsLevel.playSound(
                    target, target.blockPosition(),
                    SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.HOSTILE,
                    1F,1.25F
            );

            new BukkitRunnable(){
                int tickCount = 0;

                @Override
                public void run() {
                    tickCount++;

                    if(tickCount >= castDuration) this.cancel();

                    if(tickCount % 15 == 0){
                        Vec3 newDir = target.position().subtract(boss.position()).normalize();

                        SleepyProjectiles.DARK_SPARK.shootLinear(nmsLevel, boss, boss.position(), newDir);

                        nmsLevel.playSound(
                                null, boss.blockPosition(),
                                SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE,
                                0.85F,1.75F
                        );
                    }
                }
            }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
        }

        @Override public double getMinDistance(){ return 1.0D; }
        @Override public double getMaxDistance(){ return 10.0D; }
        @Override public int getWindupTicks(){ return 40; }
        @Override public int getRecoveryTicks(){ return 40; }
        @Override public int getCooldownTicks(){ return 50; }
        @Override public int getWeight(){ return 10; }
    }
}