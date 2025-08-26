package org.blackstamp.sleepyChronicles.listener.entity.llama;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();
        DamageType damageType = e.getDamageSource().getDamageType();
        globalClass global = new globalClass();

        if(global.getServerDay() >= 6){
            if (entity instanceof Player && damager instanceof Llama && damageType.equals(DamageType.SPIT)) {
            e.setDamage(999);

            }
        }
    }
}
