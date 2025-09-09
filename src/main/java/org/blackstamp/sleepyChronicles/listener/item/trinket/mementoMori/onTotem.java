package org.blackstamp.sleepyChronicles.listener.item.trinket.mementoMori;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
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
    globalClass global = new globalClass();

    @EventHandler
    private void onTotem(EntityResurrectEvent e){
        Entity entity = e.getEntity();

        if(entity instanceof Player p){
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.createMementoMori())){
                if(r.nextBoolean()){
                    p.sendActionBar("§a50/50");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1.25F);
                    Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
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
