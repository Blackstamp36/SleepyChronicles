package org.blackstamp.sleepyChronicles.listener.item.misc.sniperRifle;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.CooldownManager;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

@Registrable
public class onInteract implements Listener {
    String itemDataComponent = "sniper_rifle";
    globalClass global = new globalClass();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        PlayerInventory inventory = p.getInventory();
        ItemStack main = inventory.getItemInMainHand();

        if (!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if (!global.isCustomItem(main, itemDataComponent)) return;

        if (!CooldownManager.isOnCooldown(p, itemDataComponent)) { // add sniper logic
            Snowball bullet = p.launchProjectile(Snowball.class);
            modifyToSniperProjectile(p, bullet);

            CooldownManager.setCooldown(p, itemDataComponent, main, 5 * 1000);

        } else CooldownManager.showCooldown(p, itemDataComponent);

    }

    private void modifyToSniperProjectile(Player shooter, Snowball projectile){
        Location spawnLoc = shooter.getLocation();

        projectile.setShooter(shooter);
        projectile.getScoreboardTags().add("sniperProjectile");

        shooter.playSound(spawnLoc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5F, 0.5F);
        shooter.playSound(spawnLoc, Sound.BLOCK_BREWING_STAND_BREW, 0.85F, 1.5F);
        global.spawnParticles(spawnLoc, Particle.ELECTRIC_SPARK, null, 25);
    }
}

