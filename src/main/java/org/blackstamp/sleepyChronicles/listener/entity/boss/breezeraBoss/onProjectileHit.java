package org.blackstamp.sleepyChronicles.listener.entity.boss.breezeraBoss;

import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

@Registrable
public class onProjectileHit implements Listener {
    int seedBaseDamage = 5;
    Random r = new Random();

@EventHandler
    private void onProjectileHit(ProjectileHitEvent e){
    Entity projectile = e.getEntity();

    if(!(e.getHitEntity() instanceof Player p)) return;

    if(projectile.getScoreboardTags().contains("greenSeed")){
        p.damage(seedBaseDamage);
        p.playSound(p.getLocation(), Sound.BLOCK_AZALEA_PLACE,1,1.25F);
        if(r.nextInt(1,101) <= 4) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
            p.sendActionBar(ChatColor.of("#5f940c") + "Unlucky! You got poisoned!");
            p.playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_DEATH,1,1.25F);
        }
    } else if(projectile.getScoreboardTags().contains("spikySeed")){
        p.damage(seedBaseDamage * 3);
        p.playSound(p.getLocation(), Sound.BLOCK_AZALEA_PLACE,1,0.25F);

        if(r.nextInt(1,101) <= 9) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
            p.sendActionBar(ChatColor.of("#5f940c") + "Unlucky! You got poisoned!");
            p.playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_DEATH,1,1.25F);
        }
    }

    }
}
