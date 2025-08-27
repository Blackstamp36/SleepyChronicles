package org.blackstamp.sleepyChronicles.listener.item.myWish;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.blackstamp.sleepyChronicles.util.CooldownManager;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

@Registrable
public class onDamageToP implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        globalClass global = new globalClass();
        Entity entity = e.getEntity();

        if(entity instanceof Player p) {
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if (p.getHealth() <= 7) {

                if (perksInv.contains(trinkets.createMyWish()) && !CooldownManager.isOnCooldown(p, "my_wish")) {
                    CooldownManager.setCooldown(p, "my_wish", trinkets.createMyWish(), 600 * 1000);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 1, true,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1, true,false));

                } else if (perksInv.contains(trinkets.createMyWish()) && CooldownManager.isOnCooldown(p, "my_wish")) {
                    CooldownManager.showTrinketCooldown(p, "my_wish");
                }

                if (perksInv.contains(trinkets.createYourWish()) && !CooldownManager.isOnCooldown(p, "your_wish")) {
                    CooldownManager.setCooldown(p, "your_wish", trinkets.createYourWish(), 300 * 1000);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 3, true,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1, true,false));

                } else if (perksInv.contains(trinkets.createMyWish()) && CooldownManager.isOnCooldown(p, "your_wish")) {
                    CooldownManager.showTrinketCooldown(p, "your_wish");
                }


            }
        }
    }
}
