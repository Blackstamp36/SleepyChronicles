package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onToggleSneak implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e){
        Player p = e.getPlayer();

        if (!e.isSneaking()) {
            return;
        }

        GlobalClass global = new GlobalClass();
        PlayerData data = global.getPlayerData(p.getUniqueId());
        Inventory perksInv = data.getTrinketsAsInventory(p);

        if(perksInv.contains(trinkets.createGhostlyEssence()) && !CooldownManager.isOnCooldown(p, "ghostly_essence")){
            CooldownManager.setCooldown(p, "ghostly_essence", trinkets.createGhostlyEssence(),600 * 1000);
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAVING, 300, 0, true,false));

        } else if(perksInv.contains(trinkets.createGhostlyEssence()) && CooldownManager.isOnCooldown(p, "ghostly_essence")){
            CooldownManager.showCooldown(p, "ghostly_essence");
        }

        if(perksInv.contains(trinkets.createGhostlySoul()) && !CooldownManager.isOnCooldown(p, "ghostly_soul")){
            CooldownManager.setCooldown(p, "ghostly_soul", trinkets.createGhostlySoul(),300 * 1000);
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAVING, 600, 0, true,false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1, true,false));

        } else if(perksInv.contains(trinkets.createGhostlySoul()) && CooldownManager.isOnCooldown(p, "ghostly_soul")){
            CooldownManager.showCooldown(p, "ghostly_soul");
            }
        }
    }

