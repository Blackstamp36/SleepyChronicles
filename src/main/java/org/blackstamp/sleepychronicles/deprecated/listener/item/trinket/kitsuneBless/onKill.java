package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.kitsuneBless;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onKill implements Listener {
    trinketItems trinkets = new trinketItems();
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onKill(EntityDeathEvent e){

        if(e.getEntity().getKiller() != null){
            Player p = e.getEntity().getKiller();
            PlayerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createKitsuneHeart())){
                p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,100,0));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.5F,1.5F);
            }
        }
    }
}
