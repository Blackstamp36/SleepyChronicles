package org.blackstamp.sleepyChronicles.listener.item.vortex.vortexShortbow;

import net.minecraft.server.level.ServerLevel;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.blackHole;
import org.blackstamp.sleepyChronicles.util.color.ChatColor;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

@Registrable
public class vortexShortbow implements Listener {
    int initialValue = 0;
    int hitsNeeded = 10;
    globalClass global = new globalClass();
    public HashMap<UUID, Integer> vortexCounter = new HashMap<>();

    @EventHandler
    private void onHit(EntityDamageEvent e){
        Entity entity = e.getEntity();
        Entity causingEntity = e.getDamageSource().getCausingEntity();

        if(!(entity instanceof Monster)) return;
        if(!(causingEntity instanceof Player p)) return;
        if(e.isCancelled()) return;
        if(!e.getDamageSource().getDamageType().equals(DamageType.ARROW)) return;
        if(!global.isCustomItem(p.getInventory().getItemInMainHand(), "vortex_shortbow")) return;
        if (!global.hasCustomArmor(p, "vortex")) return;

        UUID uuid = p.getUniqueId();
        vortexCounter.putIfAbsent(p.getUniqueId(), initialValue);
        vortexCounter.put(uuid, vortexCounter.get(uuid) + 1);


        if (vortexCounter.get(uuid) < hitsNeeded) {
            p.sendActionBar(ChatColor.of("#4dcbcb") + vortexCounter.get(uuid).toString());
            p.playSound(p, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 0.5F, 0.25F);

        } else if (vortexCounter.get(uuid) >= hitsNeeded) {
            vortexCounter.put(uuid, initialValue);
            spawnBlackHole(p, entity);

        }
    }

    private void spawnBlackHole(Player summoner, Entity entity) {
        Location l = entity.getLocation();
        ServerLevel nmsLvl = ((CraftWorld) l.getWorld()).getHandle();
        blackHole e = new blackHole(net.minecraft.world.entity.EntityType.CREEPER, nmsLvl);
        e.setPos(l.getX(), l.getY(), l.getZ());
        e.setSummonerUUID(summoner.getUniqueId());
        nmsLvl.addFreshEntity(e);

        summoner.sendActionBar(ChatColor.of("#4dcbcb") + "Blackhole spawned!");
        summoner.playSound(summoner, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
        summoner.playSound(summoner, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
        summoner.playSound(summoner, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
        }

        public void cleanupVortexCounter (UUID playerId){
            vortexCounter.remove(playerId);
        }

        @EventHandler
        private void onQuit (PlayerQuitEvent e){
            Player p = e.getPlayer();
            cleanupVortexCounter(p.getUniqueId());

        }
    }

