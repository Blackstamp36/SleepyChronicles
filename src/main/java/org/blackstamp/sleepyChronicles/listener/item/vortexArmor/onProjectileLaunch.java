package org.blackstamp.sleepyChronicles.listener.item.vortexArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@Registrable
public class onProjectileLaunch implements Listener {
    Random r = new Random();
    globalClass global = new globalClass();

    @EventHandler
    private void onProjectileLaunch(ProjectileLaunchEvent e){

        if(!(e.getEntity().getShooter() instanceof Player p)) return;
        if(!p.getGameMode().equals(GameMode.SURVIVAL)) return;
        if(!global.hasCustomArmor(p, "vortex")) return;
        if(r.nextInt(1,101) > 50) return;

        Projectile projectile = e.getEntity();

        p.getInventory().addItem(getProjectileItem(projectile));
    }

    private ItemStack getProjectileItem(Projectile projectile) {
        return switch (projectile.getType()) {
            case ARROW -> new ItemStack(Material.ARROW);
            case SPECTRAL_ARROW -> new ItemStack(Material.SPECTRAL_ARROW);
            case SNOWBALL -> new ItemStack(Material.SNOWBALL);
            case EGG -> new ItemStack(Material.EGG);
            default -> new ItemStack(Material.AIR);
        };

    }

}
