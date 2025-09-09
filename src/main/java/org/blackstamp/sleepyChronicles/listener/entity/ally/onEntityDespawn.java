package org.blackstamp.sleepyChronicles.listener.entity.ally;

import net.minecraft.world.entity.Entity;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRemoveEvent;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.playerSummons;

@Registrable
public class onEntityDespawn implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        handleSummonRemoval(e.getEntity());

        if(e.getEntity().getScoreboardTags().contains("allyMob")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onEntityRemove(EntityRemoveEvent event) {
            handleSummonRemoval(event.getEntity());
        }

    private void handleSummonRemoval(org.bukkit.entity.Entity entity) {
        if (!(entity instanceof CraftEntity craftEntity)) return;
        Entity nmsEntity = craftEntity.getHandle();

        if(!(nmsEntity instanceof summonableMob ally)) return;
        if(nmsEntity instanceof stardustGolem) return;

        UUID summonerUUID = ally.getSummonerUUID();

        if (summonerUUID != null) {
                    Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                        Integer currentSummons = playerSummons.get(summonerUUID);
                        if (currentSummons != null
                                && currentSummons > 0) playerSummons.put(summonerUUID, currentSummons - 1);
                    },1);
        }



    }
}
