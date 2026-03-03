package org.blackstamp.sleepychronicles.game.listener.player.survival.death.totem;

import org.blackstamp.sleepychronicles.api.chat.ChatUtils;
import org.blackstamp.sleepychronicles.api.constant.ConstantColors;
import org.blackstamp.sleepychronicles.api.player.survival.DamageUtils;
import org.blackstamp.sleepychronicles.api.player.survival.death.totem.TotemUtils;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.EquipmentSlot;

@Registrable
public class TotemListener implements Listener {

    @EventHandler
    public void totem(EntityResurrectEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        EquipmentSlot totem = e.getHand();

        if(totem == null) return;

        final int number = TotemManager.get(p);
        final String type = TotemUtils.getTotemType(p.getInventory().getItem(totem));

        EntityDamageEvent damageEvent = p.getLastDamageCause();
        EntityDamageEvent.DamageCause cause = (damageEvent != null)
                ? damageEvent.getCause() : EntityDamageEvent.DamageCause.CUSTOM;

        final String message = ConstantColors.RED + p.getName() + " popped a " + type + "!\n" +
                ConstantColors.TOTEM + "«N°" + number + "»" + ConstantColors.GRAY + " Cause of usage: " + DamageUtils.getCauseString(p, cause) + ".";

        TotemManager.set(p, number + 1);
        ChatUtils.sendBroadcast(message);
    }
}
