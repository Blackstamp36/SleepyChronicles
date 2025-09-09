package org.blackstamp.sleepyChronicles.listener.entity.enderman.nightMan;

import com.destroystokyo.paper.event.entity.EndermanEscapeEvent;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Registrable
public class onEndermanEscape implements Listener {

    @EventHandler
    private void onEndermanEscape(EndermanEscapeEvent e) {
        Entity entity = e.getEntity();

        if(entity.getScoreboardTags().contains("nightMan")) {
            if(e.getReason().equals(EndermanEscapeEvent.Reason.DROWN)){
                e.setCancelled(true);
            }

        }
    }
}

