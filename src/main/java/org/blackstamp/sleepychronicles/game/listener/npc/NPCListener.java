package org.blackstamp.sleepychronicles.game.listener.npc;

import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.npc.MobInteraction;
import org.blackstamp.sleepychronicles.api.mobs.npc.SleepyNPCs;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataType;

@Registrable
public class NPCListener implements Listener {

    @EventHandler
    public void onInteraction(PlayerInteractEntityEvent e){
        Entity mob = e.getRightClicked();

        String id = PersistentData.get(mob, SleepyKeys.MOB_ID, PersistentDataType.STRING);

        if(id == null) return;

        MobInteraction interaction = SleepyNPCs.getInteraction(id);

        if(interaction == null) return;
        e.setCancelled(true);

        interaction.onInteraction(e);
    }
}
