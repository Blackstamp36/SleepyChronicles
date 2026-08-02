package org.blackstamp.sleepychronicles.game.listener.dungeon;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

@Registrable
public class RunListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(!RunManager.isInRun(p.getUniqueId())) return;

        p.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 1.0F, 0.25F));
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){ // todo: end wipe condition!
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if(!RunManager.isInRun(p.getUniqueId())) return;

        SleepyParty party = PartyManager.getParty(uuid);
    }

    private void checkForWipeCondition(SleepyParty party){}
}
