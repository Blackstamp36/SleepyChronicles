package org.blackstamp.sleepyChronicles.listener.entity.boss.mechanicalEye;

import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof Wither && entity.getScoreboardTags().contains("mechanicalEye")) {
                if(e.getDamageSource().getDamageType().equals(DamageType.IN_WALL)){
                    entity.remove();
                }
            }
        }
    }
