package org.blackstamp.sleepychronicles.global.utils.manager;

import net.minecraft.world.entity.player.Player;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class CollisionManager {

    public boolean checkCollision(Entity entity, Entity target) {
        BoundingBox projectileBox = entity.getBoundingBox();
        BoundingBox targetBox = target.getBoundingBox();

        return projectileBox.overlaps(targetBox);
    }

    public List<net.minecraft.world.entity.Entity> getPlayerCollisions(net.minecraft.world.entity.Entity projectile){
        return projectile.level().getEntities(projectile, projectile.getBoundingBox(),
                entity -> entity instanceof Player);
    }
}
