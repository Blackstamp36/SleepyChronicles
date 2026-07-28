package org.blackstamp.sleepychronicles.game.mobs.custom.projectiles;

import net.minecraft.world.item.Item;
import org.bukkit.Particle;

public class ProjectileSettings {
    public final Particle particle;
    public final int particleAmount;
    public final Item item;
    public final float explosionRadius;
    public final int lifetime;
    public final int speed;
    public final float damage;

    public ProjectileSettings(Particle particle, int particleAmount, Item item, float explosionRadius, int lifetime, int speed, float damage){
        this.particle = particle;
        this.particleAmount = particleAmount;
        this.item = item;
        this.explosionRadius = explosionRadius;
        this.lifetime = lifetime;
        this.speed = speed;
        this.damage = damage;
    }
}
