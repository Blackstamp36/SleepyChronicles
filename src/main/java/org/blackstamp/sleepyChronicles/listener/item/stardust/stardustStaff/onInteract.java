package org.blackstamp.sleepyChronicles.listener.item.stardust.stardustStaff;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.listener.item.vortex.vortexShortbow.vortexShortbow;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.stardustCreeper;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.stardustPhantom;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.vex.stardustVex;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

@Registrable
public class onInteract implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        PlayerInventory inventory = p.getInventory();
        ItemStack main = inventory.getItemInMainHand();

        if (!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if (!global.isCustomItem(main, "stardust_staff")) return;

        if (!CooldownManager.isOnCooldown(p, "stardust_staff")) { // add summon logic
            spawnRandomStardustMob(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,1, true, false));
            p.playSound(l, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,0.5F,0.5F);
            p.playSound(l, Sound.BLOCK_BREWING_STAND_BREW,0.85F,1.5F);
            global.spawnParticles(l, Particle.ELECTRIC_SPARK,null,25);
            CooldownManager.setCooldown(p, "stardust_staff", main, 6 * 1000);

        } else CooldownManager.showCooldown(p, "stardust_staff");

    }

    private void spawnRandomStardustMob(Player summoner){
        Location l = summoner.getLocation();
        Random r = new Random();
        int chance = r.nextInt(1,4);

        if(chance == 2) l = new Location(l.getWorld(), l.getX(), l.getY() + 1.5, l.getZ());

        switch(chance){
            case 1 -> stardustPhantom.spawnEntity(l, 1, summoner);
            case 2 -> stardustVex.spawnEntity(l, 1, summoner);
            case 3 -> stardustCreeper.spawnEntity(l, 1, summoner);
        }
    }
}

