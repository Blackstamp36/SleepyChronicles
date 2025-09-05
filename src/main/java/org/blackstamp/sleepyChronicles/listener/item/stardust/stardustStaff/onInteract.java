package org.blackstamp.sleepyChronicles.listener.item.stardust.stardustStaff;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.listener.item.vortex.vortexShortbow.vortexShortbow;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.stardustPhantom;
import org.blackstamp.sleepyChronicles.util.CooldownManager;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

@Registrable
public class onInteract implements Listener {
    vortexShortbow vS = new vortexShortbow();
    globalClass global = new globalClass();
    private final Random r = new Random();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        PlayerInventory inventory = p.getInventory();
        ItemStack main = inventory.getItemInMainHand();

        if (!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if (!global.isCustomItem(main, "stardust_staff")) return;

        if (!CooldownManager.isOnCooldown(p, "stardust_staff")) { // add summon logic
            stardustPhantom.spawnEntity(l, 1, p);
            p.playSound(l, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,0.5F,0.5F);
            p.playSound(l, Sound.BLOCK_BREWING_STAND_BREW,0.85F,1.5F);
            global.spawnParticles(l, Particle.ELECTRIC_SPARK,null,25);
            CooldownManager.setCooldown(p, "stardust_staff", main, 10 * 1000);

        } else CooldownManager.showCooldown(p, "stardust_staff");

    }
}

