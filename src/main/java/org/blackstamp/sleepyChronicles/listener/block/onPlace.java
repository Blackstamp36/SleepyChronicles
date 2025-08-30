package org.blackstamp.sleepyChronicles.listener.block;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

@Registrable
public class onPlace implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if(p.hasPotionEffect(PotionEffectType.WEAVING)){
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.75F,0.25F);
            e.setCancelled(true);

        }
    }

}
