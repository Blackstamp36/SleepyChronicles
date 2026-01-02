package org.blackstamp.sleepychronicles.deprecated.listener.item.solar.solarSword;

import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.HashMap;
import java.util.UUID;

@Registrable
public class solarSword implements Listener {
    int initialValue = 0;
    int hitsNeeded = 10;
    public HashMap<UUID, Integer> solarCounter = new HashMap<>();

    @EventHandler
    private void onHit(EntityDamageEvent e) {
        Entity damagedEntity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();

        if(!(damagedEntity instanceof Enemy)) return;
        if(!(causingEntity instanceof Player p)) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) return;
        if(e.isCancelled()) return;

        ItemStack main = p.getInventory().getItemInMainHand();
        UUID uuid = p.getUniqueId();
        Location l = damagedEntity.getLocation();

        if(!isSolarSword(main)) return;

        solarCounter.putIfAbsent(p.getUniqueId(), initialValue);
        solarCounter.put(uuid, solarCounter.get(uuid) + 1);

        // Number of hits needed to explode.
        if (solarCounter.get(uuid) < hitsNeeded) {
            p.sendActionBar(ChatColor.of("#cc9933") + solarCounter.get(uuid).toString());
            p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 0.5F, 0.25F);

        } else if (solarCounter.get(uuid) >= hitsNeeded) {
            solarCounter.put(uuid, initialValue);
            doSolarExplosion(p, l);

        }
    }

    private boolean isSolarSword(ItemStack main) {
        if (main.hasItemMeta()) {
            ItemMeta mainMeta = main.getItemMeta();

            if (mainMeta.hasCustomModelDataComponent()) {
                CustomModelDataComponent data = mainMeta.getCustomModelDataComponent();

                return data.getStrings().contains("solar_sword");
            }
        }

        return false;
    }

    private void doSolarExplosion(Player p, Location l){
        ParticleManager particleManager = new ParticleManager(l.getWorld());

        p.sendActionBar(ChatColor.of("#cc9933") + "Explosion emitted!");
        p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
        p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
        p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 2);
        p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
        particleManager.spawnParticle(l, Particle.EXPLOSION_EMITTER,null,
                1,0,0,0,1.0);

        for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(6, 3, 6)) {
            if (nearbyMonsters instanceof Player || nearbyMonsters.isInvulnerable()) continue;
            nearbyMonsters.damage(2 * p.getHealth());
        }

    }

    public void cleanupSolarCounter(UUID playerId) {
        solarCounter.remove(playerId);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        cleanupSolarCounter(p.getUniqueId());

    }
}
