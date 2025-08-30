package org.blackstamp.sleepyChronicles.listener.item.solarSword;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creaking.bobCreaking;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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
    private void onHit(EntityDamageEvent e){
        Entity entity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();
        Location l = entity.getLocation();

        if(entity instanceof Monster
                && causingEntity instanceof Player p
                && !e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
                && !e.isCancelled()) {

            UUID uuid = p.getUniqueId();
            ItemStack main = p.getInventory().getItemInMainHand();

                if (main.hasItemMeta()) {
                    ItemMeta meta = main.getItemMeta();

                    if (meta != null && meta.hasCustomModelDataComponent()) {
                        CustomModelDataComponent data = meta.getCustomModelDataComponent();
                        if (data.getStrings().contains("solar_sword")) {
                            int hitsNeededToExplode = 10;
                            solarCounter.putIfAbsent(p.getUniqueId(), 0);

                            if (solarCounter.get(uuid) <= (hitsNeededToExplode - 1)) {
                                p.sendActionBar(ChatColor.of("#cc9933") + solarCounter.get(uuid).toString());
                                solarCounter.put(uuid, solarCounter.get(uuid) + 1);
                                p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 0.5F, 0.25F);

                            } else if (solarCounter.get(uuid) == hitsNeededToExplode) {
                                solarCounter.put(uuid, 0);
                                p.sendActionBar(ChatColor.of("#cc9933") + "Explosion emitted!");
                                p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
                                p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 2);
                                p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
                                global.spawnParticles(l, Particle.EXPLOSION_EMITTER, null, 1);

                                for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(6, 3, 6)) {
                                    if (nearbyMonsters instanceof Player
                                            || nearbyMonsters.getScoreboardTags().contains("bobCreaking")) return;
                                    nearbyMonsters.damage(2 * p.getHealth());
                                }
                            }
                    }
                }
            }
        }

    }
}
