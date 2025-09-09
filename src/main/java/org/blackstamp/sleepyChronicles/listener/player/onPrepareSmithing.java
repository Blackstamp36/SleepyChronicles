package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;

@Registrable
public class onPrepareSmithing implements Listener {

    Material[] forbiddenResults =
            {
                    Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
                    Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
                    Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE
            };

    @EventHandler
    private void onPrepareSmithing(PrepareSmithingEvent e){
        if(e.getInventory().getInputTemplate() == null) return;

        for(Material result : forbiddenResults){
            if(e.getInventory().getInputTemplate().getType().equals(result)) e.setResult(null);

        }

    }
}
