package org.blackstamp.sleepyChronicles.listener.item.vortexShortbow;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.blackHole;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageType;
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
public class vortexShortbow implements Listener {
    globalClass global = new globalClass();
    public HashMap<UUID, Integer> vortexCounter = new HashMap<>();

    @EventHandler
    private void onHit(EntityDamageEvent e){
        Entity entity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();
        Location l = entity.getLocation();

        if(entity instanceof Monster
                && e.getDamageSource().getDamageType().equals(DamageType.ARROW)
                && !e.isCancelled()
                && causingEntity instanceof Player p){

            UUID uuid = p.getUniqueId();
            ItemStack main = p.getInventory().getItemInMainHand();

            if (main.hasItemMeta()) {
                ItemMeta meta = main.getItemMeta();

                if (meta.hasCustomModelDataComponent()) {
                    CustomModelDataComponent data = meta.getCustomModelDataComponent();
                    if (data.getStrings().contains("vortex_shortbow")) {
                        int hitsNeeded = 10;
                        vortexCounter.putIfAbsent(p.getUniqueId(), 0);

                        if (vortexCounter.get(uuid) <= (hitsNeeded - 1)) {
                            p.sendActionBar(ChatColor.of("#4dcbcb") + vortexCounter.get(uuid).toString());
                            vortexCounter.put(uuid, vortexCounter.get(uuid) + 1);
                            p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 0.5F, 0.25F);

                        } else if (vortexCounter.get(uuid) >= hitsNeeded) {
                            vortexCounter.put(uuid, 0);
                            p.sendActionBar(ChatColor.of("#4dcbcb") + "Blackhole spawned!");
                            p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
                            p.playSound(p, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
                            blackHole.spawnEntity(l, 1);

                        }
                    }
                }
            }
        }

    }

    public void cleanupVortexCounter(UUID playerId) {
        vortexCounter.remove(playerId);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        cleanupVortexCounter(p.getUniqueId());

    }
}
