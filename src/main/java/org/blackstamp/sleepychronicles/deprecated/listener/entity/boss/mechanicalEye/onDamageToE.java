package org.blackstamp.sleepychronicles.deprecated.listener.entity.boss.mechanicalEye;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    public void onDamageToE(EntityDamageEvent e){
        Entity entity = e.getEntity();

        if(e.getDamageSource().getCausingEntity() == null) return;
        Entity damager = e.getDamageSource().getCausingEntity();

        if(!(entity instanceof CraftEntity craftEntity)) return;
        if(!(damager instanceof CraftEntity craftDamager)) return;

        if(!(craftDamager.getHandle() instanceof Player p)) return;
        if(!(p.gameMode() == (GameType.SURVIVAL))) return;
        if(!(craftEntity.getHandle() instanceof mechanicalEye me)) return;

        me.setTarget(p, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
    }
}
