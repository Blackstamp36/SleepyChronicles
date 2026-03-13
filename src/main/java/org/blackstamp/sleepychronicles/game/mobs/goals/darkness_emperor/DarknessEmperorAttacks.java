package org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttacks;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.SleepyProjectiles;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum DarknessEmperorAttacks implements BossAttacks {
    HOMING_RAIN{
        private static final Random RANDOM = ThreadLocalRandom.current();
        private static final int COUNT = 8;
        private static final int RADIUS = 25;

        @Override
        public void cast(BossMob boss, LivingEntity target){
            if(!(target instanceof Player)) return;

            boss.setTickCooldown(60);

            final LivingEntity caster = boss.getEntity();
            final Vec3 startPos = new Vec3(caster.getX(), caster.getY(), caster.getZ());
            final Level nmsLevel = caster.level();
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
                        SleepyProjectiles.DARK_SPARK.shootHoming(nmsLevel, newPos, caster, target, 40 + (finalI * 10)
                        ), RANDOM.nextInt(5));
            }
        }
    },

    LEVITATION_SPELL{
        @Override
        public void cast(BossMob boss, LivingEntity target){

        }
    }
}