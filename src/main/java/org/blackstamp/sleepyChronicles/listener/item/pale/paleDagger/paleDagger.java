package org.blackstamp.sleepyChronicles.listener.item.pale.paleDagger;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.manager.ParticleManager;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

@Registrable
public class paleDagger implements Listener {
    int initialValue = 0;
    int hitsNeeded = 5;
    globalClass global = new globalClass();
    public HashMap<UUID, Integer> paleCounter = new HashMap<>();

    @EventHandler
    private void onHit(EntityDamageEvent e) {
        Entity damagedEntity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();

        if(!(damagedEntity instanceof Monster)) return;
        if(!(causingEntity instanceof Player p)) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) return;
        if(e.isCancelled()) return;

        ItemStack main = p.getInventory().getItemInMainHand();
        UUID uuid = p.getUniqueId();
        Location l = damagedEntity.getLocation();

        if(!global.isCustomItem(main, "pale_dagger")) return;

        paleCounter.putIfAbsent(p.getUniqueId(), initialValue);
        paleCounter.put(uuid, paleCounter.get(uuid) + 1);

        if (paleCounter.get(uuid) < hitsNeeded) {
            p.sendActionBar(ChatColor.of("#cfc4c3") + paleCounter.get(uuid).toString());
            p.playSound(p, Sound.BLOCK_CREAKING_HEART_BREAK, 0.5F, 0.25F);
            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_SNARE, 0.5F, 1.25F);

        } else if (paleCounter.get(uuid) >= hitsNeeded) {
            paleCounter.put(uuid, initialValue);
            doStormOfKnives(p, l);

        }
    }

    private void doStormOfKnives(Player p, Location l){
        ParticleManager particleManager = new ParticleManager(l.getWorld());

        p.sendActionBar(ChatColor.of("#cfc4c3") + "Storm deployed!");
        p.playSound(p, Sound.ENTITY_CREAKING_DEATH, 0.25F, 1.5F);
        p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 0.25F, 2);
        p.playSound(p, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.25F, 2);
        p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 0.25F, 0);
        particleManager.spawnParticle(l, Particle.SWEEP_ATTACK,null,
                30,0.75,0.5,0.75,1.25);

        for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(3, 1, 3)) {
            if (nearbyMonsters instanceof Player || nearbyMonsters.isInvulnerable()) continue;
            nearbyMonsters.damage(p.getHealth() * 0.75);
        }

    }

    public void cleanupPaleCounter(UUID uuid) {
        paleCounter.remove(uuid);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        cleanupPaleCounter(p.getUniqueId());
    }
}
