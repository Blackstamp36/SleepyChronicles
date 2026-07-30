package org.blackstamp.sleepychronicles.game.listener.player.trinkets;

import org.blackstamp.sleepychronicles.api.inventory.menu.trinkets.TrinketBag;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.player.PlayerUtils;
import org.blackstamp.sleepychronicles.api.player.survival.death.totem.TotemUtils;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@Registrable
public class MementoMori implements Listener {

    private static final PotionEffect[] mementoBuff = {
            new PotionEffect(PotionEffectType.RESISTANCE,15 * 20,1),
            new PotionEffect(PotionEffectType.INSTANT_HEALTH,1,1)
    };

    @EventHandler
    public void trinket(EntityResurrectEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!TrinketBag.hasTrinket(p,SleepyItems.MEMENTO_MORI.getID())) return;

        boolean rand = ThreadLocalRandom.current().nextBoolean();

        if(rand) PlayerUtils.addPots(p, mementoBuff);
        else PlayerUtils.clearPots(p, TotemUtils.totemEffects);
    }
}
