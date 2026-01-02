package org.blackstamp.sleepychronicles.deprecated.listener.item.solar.solarArmor;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onToggleSneak implements Listener {

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (!e.isSneaking()) {
            return;
        }

        GlobalClass global = new GlobalClass();

        if(global.hasCustomArmor(p, "solar")){
            if(!CooldownManager.isOnCooldown(p, "solar_shield")){
            CooldownManager.setCooldown(p, "solar_shield", null, 240 * 1000);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.25F,1.25F);
            p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN,0.5F,1.5F);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,0.5F,1.25F);
            p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,200,2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,200,1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,200,0));
            } else {
                CooldownManager.showCooldown(p, "solar_shield");
            }
        }

    }
}
