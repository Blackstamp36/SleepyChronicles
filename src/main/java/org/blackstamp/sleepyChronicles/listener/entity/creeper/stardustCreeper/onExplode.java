package org.blackstamp.sleepyChronicles.listener.entity.creeper.stardustCreeper;

import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

@Registrable
public class onExplode implements Listener {

    @EventHandler
    private void onExplode(EntityExplodeEvent e){
        Entity entity = e.getEntity();

        if (!(entity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();
        if(!(nmsEntity instanceof summonableMob)) return;

        e.blockList().clear();

    }
}
