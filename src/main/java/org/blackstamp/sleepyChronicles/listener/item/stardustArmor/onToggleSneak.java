package org.blackstamp.sleepyChronicles.listener.item.stardustArmor;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepyChronicles.util.CooldownManager;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

@Registrable
public class onToggleSneak implements Listener {

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (!e.isSneaking()) return;

        globalClass global = new globalClass();

        if(global.hasCustomArmor(p, "stardust")){
            if(!CooldownManager.isOnCooldown(p, "stardust_golem")){

            CooldownManager.setCooldown(p, "stardust_golem", null, 120 * 1000);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.25F,1.25F);
            p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN,0.5F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_DEATH,0.5F,1.25F);
            stardustGolem.spawnEntity(p.getLocation(), 1, p);
            } else {
                CooldownManager.showCooldown(p, "stardust_golem");
            }
        }

    }
}
