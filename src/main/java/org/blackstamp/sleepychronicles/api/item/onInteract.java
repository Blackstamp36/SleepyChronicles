package org.blackstamp.sleepychronicles.api.item;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class onInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){ // todo: add abilities!
        ItemStack item = e.getItem();
        if(item == null || !item.hasItemMeta()) return;

        String id = item.getItemMeta().getPersistentDataContainer().get(SleepyKeys.ITEM_ID, PersistentDataType.STRING);
        if(id == null) return;

        ItemAbility ability = SleepyItems.getAbility(id);

        if(ability != null){ ability.onInteract(e); }
    }
}
