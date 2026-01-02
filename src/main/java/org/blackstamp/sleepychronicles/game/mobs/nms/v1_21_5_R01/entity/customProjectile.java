package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity;

import net.minecraft.world.entity.LivingEntity;
import org.blackstamp.sleepychronicles.global.utils.manager.CollisionManager;

public interface customProjectile {
    CollisionManager cM = new CollisionManager();

    void registerAttributes();
    void handleProjectileImpact(LivingEntity damagedEntity);
}
