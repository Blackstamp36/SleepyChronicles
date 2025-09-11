package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.ghast.eodPhase1;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.ghast.emperorOfDarkness;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.EnumSet;

public class eodDashGoal extends Goal {
        private final emperorOfDarkness entity;
        private final double minDistance;
        private int dashDamage = 18;
        private final int particleCount = 1;

        public eodDashGoal(emperorOfDarkness entity, double minDistance) {
            this.entity = entity;
            this.minDistance = minDistance;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Entity target = entity.getTarget();

            if(!(target instanceof Player p)) return false;
            org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();

            double distanceToTarget = entity.distanceToSqr(p);

            return distanceToTarget <= minDistance
                    && bukkitP.getGameMode().equals(GameMode.SURVIVAL);
        }

        @Override
        public void tick() {
            super.tick();

            Entity target = entity.getTarget();
            if(!(target instanceof Player p)) return;

            checkDistanceToPlayer(p);
        }

        @Override
        public void start() {
        entity.increaseGoalCooldown(30);
        }

        @Override
        public boolean isInterruptable() {
        return false;
        }

        private void checkDistanceToPlayer(Player p){
            Location l = entity.getBukkitLivingEntity().getLocation();
            ParticleManager pM = new ParticleManager(l.getWorld());
            org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();

            entity.getMoveControl().setWantedPosition(p.getX(), p.getY() + 3, p.getZ(), 1.25);
            bukkitP.damage(dashDamage, entity.getBukkitLivingEntity());
            bukkitP.playSound(bukkitP.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.75F,1.25F);
            bukkitP.playSound(bukkitP.getLocation(), Sound.ENTITY_ALLAY_ITEM_TAKEN,0.85F,1.25F);
            pM.spawnParticle(l, Particle.EXPLOSION_EMITTER,null,
                    particleCount,0,0,0,1.0);
        }
}
