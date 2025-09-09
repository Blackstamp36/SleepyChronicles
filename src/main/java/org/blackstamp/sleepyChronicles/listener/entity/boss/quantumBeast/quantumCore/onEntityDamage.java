package org.blackstamp.sleepyChronicles.listener.entity.boss.quantumBeast.quantumCore;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onEntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof quantumCore core)) return;
        quantumBeast owner = core.getOwner();

        if(core.getBossPhase() == 1 && core.getHealth() <= (core.getMaxHealth() * 0.5)) {
            core.initSecondPhase(core);
            owner.initSecondPhase(owner);

            for(Player nearby : core.getBukkitLivingEntity().getLocation().getNearbyPlayers(35)) {
                nearby.playSound(nearby.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.5F, 1.75F);
                nearby.playSound(nearby.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 0.5F, 0.75F);

            }
        }

    }
}
