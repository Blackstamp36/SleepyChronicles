package org.blackstamp.sleepychronicles.deprecated.listener.item.trinket.mementoMori;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

@Registrable
public class onTotem implements Listener {
    Random r = new Random();
    trinketItems trinkets = new trinketItems();
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onTotem(EntityResurrectEvent e){
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            PlayerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createMementoMori())){
                if(r.nextBoolean()){
                    p.sendActionBar("§a50/50");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1.25F);
                    Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,300,1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH,1,1));
                    }, 1);

                } else {
                    p.sendActionBar("§c50/50");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,0.25F);
                    global.removeTotemInitialEffects(p);

                }

            }


        }
    }
}
