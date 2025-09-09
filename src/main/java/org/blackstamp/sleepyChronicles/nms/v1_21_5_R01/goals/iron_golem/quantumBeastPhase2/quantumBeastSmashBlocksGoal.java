package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.iron_golem.quantumBeastPhase2;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;

public class quantumBeastSmashBlocksGoal extends Goal {
    int smashCooldown = 0;
    private final int breakRadius;

    private final quantumBeast entity;

    public quantumBeastSmashBlocksGoal(quantumBeast entity, int breakRadius) {
        this.entity = entity;
        this.breakRadius = breakRadius;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();
        org.bukkit.entity.LivingEntity bukkitEntity = entity.getBukkitLivingEntity();

        if (target == null) return false;
        if (!(target instanceof Player p)) return false;

        org.bukkit.entity.Player bukkitP = (org.bukkit.entity.Player) p.getBukkitLivingEntity();
        double distanceToTarget = bukkitEntity.getLocation().distance(bukkitP.getLocation());

        return distanceToTarget >= 4.5
                && !entity.hasLineOfSight(target);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if (!(target instanceof Player p)) return;

        if (smashCooldown <= 0) {
           smashBlocksNearby(entity.blockPosition());

        } else smashCooldown--;
    }

    private void smashBlocksNearby(BlockPos pos) {
        BlockPos entityPos = entity.blockPosition();
        Level nmsLevel = entity.level();
        int radius = breakRadius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = entityPos.offset(x, y, z);
                    if (entityPos.distSqr(checkPos) <= (breakRadius * breakRadius))
                        nmsLevel.destroyBlock(pos, false);
                }
            }
        }

        smashCooldown = 20;
    }
}
