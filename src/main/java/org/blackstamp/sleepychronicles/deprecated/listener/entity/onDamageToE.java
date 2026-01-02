package org.blackstamp.sleepychronicles.deprecated.listener.entity;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onEntityDamage(EntityDamageEvent e) {
        GlobalClass global = new GlobalClass();
        Entity entity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();
        DamageType damageType = e.getDamageSource().getDamageType();

        if(causingEntity == null) return;
        if(!(global.getServerDay() >= 6)) return;
        if(entity instanceof Player) if(causingEntity instanceof summonableMob) e.setCancelled(true);
        if(causingEntity instanceof Player
                && (damageType.equals(DamageType.EXPLOSION) || damageType.equals(DamageType.PLAYER_EXPLOSION))){
            e.setCancelled(true);
            return;
        }
    }
}


