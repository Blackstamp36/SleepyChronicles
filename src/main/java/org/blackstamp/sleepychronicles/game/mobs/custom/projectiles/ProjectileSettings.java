package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles;

import org.bukkit.Particle;

public class ProjectileSettings {
    public final Particle particle;
    public final float explosionRadius;
    public final int lifetime;
    public final int speed;
    public final float damage;

    public ProjectileSettings(Particle particle, float explosionRadius, int lifetime, int speed, float damage){
        this.particle = particle;
        this.explosionRadius = explosionRadius;
        this.lifetime = lifetime;
        this.speed = speed;
        this.damage = damage;
    }
}
