package org.blackstamp.sleepychronicles.deprecated.listener.entity.phantom.seekerPhantom;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();
        Random r = new Random();

        if (entity instanceof Player p && damager instanceof Phantom && entity.getScoreboardTags().contains("seekerPhantom")) {
            int randomInt = r.nextInt(1, 10);

            p.setLevel(Math.max(p.getLevel() - randomInt, 0));

            p.sendMessage(chatPrefix + "§cA seeker stole from you " + randomInt + " levels of experience!");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.75F, 0.5F);
        }
    }
}
