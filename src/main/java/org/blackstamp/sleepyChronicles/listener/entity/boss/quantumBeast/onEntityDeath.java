package org.blackstamp.sleepyChronicles.listener.entity.boss.quantumBeast;

import net.minecraft.world.entity.LivingEntity;
import org.blackstamp.sleepyChronicles.item.trinket.trinketItems;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class onEntityDeath implements Listener {
    trinketItems trinkets = new trinketItems();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if(!(e.getEntity() instanceof CraftEntity craftEntity)) return;
        if(!(craftEntity.getHandle() instanceof LivingEntity entity)) return;

        if(entity instanceof quantumCore || entity instanceof quantumBeast) {
            e.setDroppedExp(0);
            e.getDrops().clear();

            if(!(entity instanceof quantumCore)) return;
            if(e.getEntity().getKiller() == null) return;
            if(!(ThreadLocalRandom.current().nextInt(0, 100) <= 65)) return; // 66%
            e.getDrops().add(trinkets.createQuantumCore());
        }
    }
}
