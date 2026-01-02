package org.blackstamp.sleepychronicles.deprecated.listener.item.vortex.vortexArmor;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class onProjectileHit implements Listener {

    @EventHandler
    private void onProjectileHit(EntityDamageEvent e) {
        GlobalClass global = new GlobalClass();
        Entity damager = e.getDamageSource().getCausingEntity();
        Entity projectile = e.getDamageSource().getDirectEntity();

        if(!(e.getEntity() instanceof Enemy)) return;
        if(e.isCancelled() || e.getEntity().isInvulnerable()) return;
        if(!(projectile instanceof Projectile pr && damager instanceof Player p)) return;

        double originalDamage = e.getDamage();
        double modifiedDamage;

        if(!global.hasCustomArmor(p, "vortex")) return;
        modifiedDamage = originalDamage + (originalDamage * 0.3);
        e.setDamage(modifiedDamage);

        if(!ThreadLocalRandom.current().nextBoolean()) return;
        p.getInventory().addItem(getProjectileItem(pr));

        }

    private ItemStack getProjectileItem(Projectile projectile) {
        return switch (projectile.getType()) {
            case ARROW -> new ItemStack(Material.ARROW);
            case SPECTRAL_ARROW -> new ItemStack(Material.SPECTRAL_ARROW);
            default -> new ItemStack(Material.AIR);
        };

    }
}

