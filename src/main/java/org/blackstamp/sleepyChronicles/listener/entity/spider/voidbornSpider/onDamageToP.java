package org.blackstamp.sleepyChronicles.listener.entity.spider.voidbornSpider;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onDamageToP implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getCausingEntity();

        if (entity instanceof Player p && damager instanceof Spider && entity.getScoreboardTags().contains("voidbornSpider")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 2, false, false));
            p.playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 0.2F, 0.75F);

        }
    }
}
