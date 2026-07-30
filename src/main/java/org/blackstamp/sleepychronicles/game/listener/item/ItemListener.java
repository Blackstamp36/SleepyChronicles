package org.blackstamp.sleepychronicles.game.listener.item;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.item.ItemAbility;
import org.blackstamp.sleepychronicles.api.item.ItemUtils;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        ItemStack item = e.getItem();
        if(item == null || !item.hasItemMeta()) return;

        String id = item.getItemMeta().getPersistentDataContainer().get(SleepyKeys.ITEM_ID, PersistentDataType.STRING);
        if(id == null) return;

        ItemAbility ability = SleepyItems.getAbility(id);

        if(ability != null){ ability.onInteract(e); }
    }

    @EventHandler
    public void onArmorHit(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        for(ItemStack piece : p.getInventory().getArmorContents()){

            if(piece == null) continue;

            String id = ItemUtils.getID(piece.getItemMeta());

            if(id == null) continue;

            ItemAbility ability = SleepyItems.getAbility(id);
            if(ability != null) ability.onArmorHit(e,p);
        }
    }
}
