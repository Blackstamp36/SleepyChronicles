package org.blackstamp.sleepychronicles.game.listener.item.trinket;

import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.item.trinket.TrinketAbility;
import org.blackstamp.sleepychronicles.api.item.trinket.TrinketManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class TrinketListener implements Listener {
    @EventHandler
    public void onDamageTaken(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        List<String> trinketList = TrinketManager.CACHE.get(p.getUniqueId());

        if(trinketList.isEmpty()) return;

        for(String id : trinketList){
            TrinketAbility ability = SleepyItems.getTrinketAbility(id);

            if(ability != null) ability.onDamageTaken(e);
        }
    }
}
