package org.blackstamp.sleepychronicles.game.mobs.goals.darkness_emperor;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossAttacks;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.custom.projectiles.HomingProjectile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public enum DarknessEmperorAttacks implements BossAttacks {
    HOMING_RAIN{
        private final Random r = new Random();
        private final int damage = 24;
        private final int count = 8;
        private final int radius = 25;

        @Override
        public void cast(BossMob boss, LivingEntity target){
            boss.setTickCooldown(60);

            LivingEntity entity = boss.getEntity();
            org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
            bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH,0.85F,1.25F);
            bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,0.85F,1.25F);

            bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                    20,0, false,false));

            Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            Level nmsLevel = entity.level();

            for(int i = 0; i < count; i++){
                double angle = r.nextDouble() * 2 * Math.PI;
                double distance = r.nextDouble() * radius;

                double x = startPos.x() + Math.cos(angle) * distance;
                double y = startPos.y() + 30;
                double z = startPos.z() + Math.sin(angle) * distance;

                Vec3 newPos = new Vec3(x, y, z);

                HomingProjectile p = new HomingProjectile(EntityType.ARMOR_STAND, nmsLevel,
                        damage,70 + (i * 10), 40 + (i * 10),
                        target, entity);

                Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
                    p.setPos(newPos);
                    nmsLevel.addFreshEntity(p);
                }, r.nextInt(5));
            }
        }
    }
}