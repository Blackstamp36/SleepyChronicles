package org.blackstamp.sleepychronicles.deprecated.listener.day.day1.prepare_item;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

@Registrable
public class onBrew implements Listener {

    Material[] forbiddenMaterials =
            {
                    Material.COBWEB,
                    Material.SLIME_BALL
            };

    @EventHandler
    private void onBrew(BrewEvent e){
        ItemStack ingredient = e.getContents().getIngredient();

        if(ingredient == null) return;

        for(Material m : forbiddenMaterials)
            if(ingredient.getType().equals(m)){
                ingredient.setAmount(0);
                e.setCancelled(true);
            }
    }
}
