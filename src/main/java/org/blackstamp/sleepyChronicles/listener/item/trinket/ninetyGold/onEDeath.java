package org.blackstamp.sleepyChronicles.listener.item.trinket.ninetyGold;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@Registrable
public class onEDeath implements Listener {
    Random r = new Random();
    trinketItems trinkets = new trinketItems();

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        globalClass global = new globalClass();

        if (entity.getKiller() != null && entity instanceof Monster) {
            Player p = e.getEntity().getKiller();
            playerData data = global.getPlayerData(p.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(p);

            if(perksInv.contains(trinkets.create99Gold())){
                int chance = r.nextInt(0,101);

                if(chance <= 1){
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,0);
                    p.getLocation().getWorld().spawnEntity(entity.getLocation(), EntityType.CREEPER, CreatureSpawnEvent.SpawnReason.NATURAL);

                } else if (chance <= 15) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,2);
                    e.getDrops().add(new ItemStack(Material.GOLD_INGOT));

                } else if(perksInv.contains(trinkets.createGoldenTouch())){
                    if (chance <= 30) {
                        ItemStack goldIngots = new ItemStack(Material.GOLD_INGOT);

                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,2);
                        goldIngots.setAmount(r.nextInt(1,6));
                        e.getDrops().add(goldIngots);

                    }
                }
            }


        }

    }
}
