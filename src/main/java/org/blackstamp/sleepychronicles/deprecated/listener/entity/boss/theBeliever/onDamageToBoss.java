package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.theBeliever;

import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToBoss implements Listener {

    @EventHandler
    public void onDamageToE(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof theBeliever boss)) return;

        if(boss.getBossPhase() == 1 && boss.getHealth() <= (boss.getMaxHealth() * 0.5)) boss.initSecondPhase(boss);
    }
}
