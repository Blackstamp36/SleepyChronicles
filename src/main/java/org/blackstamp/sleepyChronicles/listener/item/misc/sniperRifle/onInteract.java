package org.blackstamp.sleepyChronicles.listener.item.misc.sniperRifle;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.manager.CooldownManager;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
        ParticleManager particleManager = new ParticleManager(spawnLoc.getWorld());

        projectile.setShooter(shooter);
        projectile.getScoreboardTags().add("sniperProjectile");

        shooter.playSound(spawnLoc, Sound.ITEM_CROSSBOW_SHOOT, 0.85F, 0.5F);
        particleManager.spawnParticle(spawnLoc, Particle.SMOKE,null,
                25,0,0,0,0.5);
    }
}

