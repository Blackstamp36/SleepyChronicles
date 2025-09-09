package org.blackstamp.sleepyChronicles.listener.entity.llama;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.llama.aggresiveLlama;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.damage.DamageType;
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
        globalClass global = new globalClass();

        if(!(global.getServerDay() >= 6)) return;
        if(!(entity instanceof Player)) return;
        if(!(damager instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof aggresiveLlama)) return;
        e.setDamage(999);

    }
}
