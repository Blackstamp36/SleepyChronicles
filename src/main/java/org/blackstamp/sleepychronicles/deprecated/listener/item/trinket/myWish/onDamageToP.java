package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.myWish;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();

        if(entity instanceof Player p) {
            PlayerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if (p.getHealth() <= 7) {

                if (perksInv.contains(trinkets.createMyWish()) && !CooldownManager.isOnCooldown(p, "my_wish")) {
                    CooldownManager.setCooldown(p, "my_wish", trinkets.createMyWish(), 600 * 1000);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 1, true,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1, true,false));

                } else if (perksInv.contains(trinkets.createMyWish()) && CooldownManager.isOnCooldown(p, "my_wish")) {
                    CooldownManager.showCooldown(p, "my_wish");
                }

                if (perksInv.contains(trinkets.createYourWish()) && !CooldownManager.isOnCooldown(p, "your_wish")) {
                    CooldownManager.setCooldown(p, "your_wish", trinkets.createYourWish(), 300 * 1000);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 3, true,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 1, true,false));

                } else if (perksInv.contains(trinkets.createMyWish()) && CooldownManager.isOnCooldown(p, "your_wish")) {
                    CooldownManager.showCooldown(p, "your_wish");
                }


            }
        }
    }
}
