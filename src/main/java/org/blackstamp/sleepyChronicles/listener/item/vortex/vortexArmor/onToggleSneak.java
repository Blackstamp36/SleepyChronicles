package org.blackstamp.sleepyChronicles.listener.item.vortex.vortexArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.manager.CooldownManager;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.cancelFallDamage;

@Registrable
public class onToggleSneak implements Listener {

    Vector doubleJump = new Vector(0,0.75,0);

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (!e.isSneaking()) {
            return;
        }

        globalClass global = new globalClass();

        if(global.hasCustomArmor(p, "vortex")){
            if(!CooldownManager.isOnCooldown(p, "vortex_jump")){
            CooldownManager.setCooldown(p, "vortex_jump", null, 15 * 1000);
            cancelFallDamage.put(uuid, true);
            p.setVelocity(doubleJump);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.25F,1.25F);
            } else {
                CooldownManager.showCooldown(p, "vortex_jump");
            }
        }

    }
}
