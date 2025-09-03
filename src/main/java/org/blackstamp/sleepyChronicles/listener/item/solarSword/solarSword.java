package org.blackstamp.sleepyChronicles.listener.item.solarSword;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
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
    globalClass global = new globalClass();
    public HashMap<UUID, Integer> solarCounter = new HashMap<>();

    @EventHandler
    private void onHit(EntityDamageEvent e) {
        Entity damagedEntity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();

        //

        if(!(damagedEntity instanceof Monster)) return;
        if(!(causingEntity instanceof Player p)) return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) return;
        if(e.isCancelled()) return;

        ItemStack main = p.getInventory().getItemInMainHand();
        UUID uuid = p.getUniqueId();
        Location l = damagedEntity.getLocation();

        if(!isSolarSword(main)) return;

        solarCounter.putIfAbsent(p.getUniqueId(), 0);

        int hitsNeeded = 10; // Number of hits needed to explode.
        if (solarCounter.get(uuid) <= (hitsNeeded - 1)) {
            p.sendActionBar(ChatColor.of("#cc9933") + solarCounter.get(uuid).toString());
            solarCounter.put(uuid, solarCounter.get(uuid) + 1);
            p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 0.5F, 0.25F);

        } else if (solarCounter.get(uuid) >= hitsNeeded) {
            solarCounter.put(uuid, 0);
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
        p.sendActionBar(ChatColor.of("#cc9933") + "Explosion emitted!");
        p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
        p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
        p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 2);
        p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
        global.spawnParticles(l, Particle.EXPLOSION_EMITTER, null, 1);

        for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(6, 3, 6)) {
            if (nearbyMonsters instanceof Player || nearbyMonsters.isInvulnerable()) continue;
            nearbyMonsters.damage(2 * p.getHealth());
        }

    }

    public void cleanupSolarCounter(UUID playerId) {
        // If the player disconnects, removes his counter from the static HashMap.

        solarCounter.remove(playerId);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        cleanupSolarCounter(p.getUniqueId());

    }
}
