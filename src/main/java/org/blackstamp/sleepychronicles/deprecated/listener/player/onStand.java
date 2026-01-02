package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onStand implements Listener {

    @EventHandler
    private void onStand(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        GlobalClass global = new GlobalClass();

        if (global.getServerDay() >= 6) {
            if (e.getFrom().getBlock().getType().equals(Material.WATER)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3));
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
            }

        }
    }
}
