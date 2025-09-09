package org.blackstamp.sleepyChronicles.util.manager;

import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class CollisionManager {

    public boolean checkBoundingBoxCollision(Entity entity, Entity target) {
        BoundingBox projectileBox = entity.getBoundingBox();
        BoundingBox targetBox = target.getBoundingBox();

        return projectileBox.overlaps(targetBox);
    }
}
