package org.blackstamp.sleepychronicles.game.listener.player.survival.death;

import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.player.survival.DamageUtils;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@Registrable
public class DeathListener implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent e){
        e.setShowDeathMessages(false);

        Player p = e.getPlayer();
        EntityDamageEvent damageEvent = p.getLastDamageCause();
        EntityDamageEvent.DamageCause cause = (damageEvent != null)
                ? damageEvent.getCause() : EntityDamageEvent.DamageCause.CUSTOM;

        if(p.getLastDamageCause() == null) return;

        ChatManager.sendBroadcast(BasicPalette.RED + p.getName() + " has died...\n" +
                SleepyPalette.SLEEPY + "«☠»" + BasicPalette.GRAY + " Cause of death: " + DamageUtils.getCauseString(p,cause) + ".");
    }
}
