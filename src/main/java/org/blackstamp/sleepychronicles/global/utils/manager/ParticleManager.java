package org.blackstamp.sleepychronicles.global.utils.manager;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ParticleManager {
    private final World world;

    public ParticleManager(World world) {
        this.world = world;
    }

    public void spawnParticle(Location loc, Particle particle, @Nullable Object data, int count,
                              double offsetX, double offsetY, double offsetZ, double speed) {
        world.spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, speed, data);
    }

    public void spawnSphere(Location center, Particle particle, double radius, int points,
                            double speed, @Nullable Object data) {

        for (int i = 0; i < points; i++) {
            double y = ((double) i / (points - 1)) * 2 - 1;
            double radiusAtY = radius * Math.sqrt(1 - y * y);

            double theta = 2 * Math.PI * i * 0.6180339887;

            double x = radiusAtY * Math.cos(theta);
            double z = radiusAtY * Math.sin(theta);

            Location point = center.clone().add(x, y * radius, z);
            spawnParticle(point, particle, data, 1, 0, 0, 0, speed);
        }
    }

    public void spawnLine(Location start, Location end, Particle particle,
                          double spacing, double speed, @Nullable Object data) {

        Vector direction = end.toVector().subtract(start.toVector());
        double length = direction.length();
        direction.normalize();

        int points = (int) (length / spacing);

        for (int i = 0; i <= points; i++) {
            Vector offset = direction.clone().multiply(i * spacing);
            Location point = start.clone().add(offset);
            spawnParticle(point, particle, data, 1, 0, 0, 0, speed);
        }
    }

    public void spawnCircle(Location center, Particle particle, double radius,
                            int points, double speed, @Nullable Object data) {

        Location newCenter = center.clone().add(new Vector(0,0.5,0));

        for(int i = 0; i < points; i++) {
            double angle = (2 * Math.PI) * i / points;
            double sin = Math.sin(angle) * radius;
            double cos = Math.cos(angle) * radius;

            Location pointLoc = newCenter.clone().add(new Vector(sin, 0, cos));
            spawnParticle(pointLoc, particle, data, 1, 0, 0, 0, speed);
        }
    }
}
