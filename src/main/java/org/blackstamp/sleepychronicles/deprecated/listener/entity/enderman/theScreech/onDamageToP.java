package org.blackstamp.sleepychronicles.deprecated.listener.entity.enderman.theScreech;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onDamageToP(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();

        if(entity instanceof Player p
                && damager instanceof Enderman && damager.getScoreboardTags().contains("theScreech")) {
            if(!damager.getWorld().getName().equals("world_aftermath")){
                p.teleport(global.getServerWorlds().get("AFTERMATH"));
                e.setCancelled(true);
            }

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, false, false));
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.5F, 1.5F);


        } // view why is teleporting to +300 blocks up, and why does it appear on the void.
    }
}
