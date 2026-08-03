package org.blackstamp.sleepychronicles.game.listener.dungeon;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    public void onLethalDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(!RunManager.isInRun(uuid)) return;

        boolean isLethal = (p.getHealth() - e.getFinalDamage()) <= 0;

        if(!isLethal) return;

        // So we don't 'kill' a downed player AGAIN.
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())){
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);
        p.setHealth(1.0D);

        SleepyParty party = PartyManager.getParty(uuid);
        RunInstance run = RunManager.getRunInstance(uuid);

        RunManager.setDowned(p,run);
        this.checkForWipeCondition(run);
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e){
        Entity boss = e.getEntity();
        UUID bossUUID = boss.getUniqueId();
        RunInstance run = RunManager.getBossInstance(bossUUID);

        if(run == null) return;

        // If we reach here, it means that the run is active and valid!
        this.showVictory(run);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRunInstance(uuid);

        if(run == null) return;

        // Check also if the entire party is empty.
        // If it is, then remove all remains of the run so it doesn't consume memory.
    }

    // Manager methods.

    private void showVictory(RunInstance run){
        SleepyParty party = run.getParty();

        for(UUID memberUUID : party.getMembers()){
            Player member = Bukkit.getPlayer(memberUUID);

            if(member == null || !member.isOnline()) continue;

            if(PersistentData.has(member,SleepyKeys.IS_DOWNED.get())){ RunManager.revivePlayer(member); }

            // TODO: tp to lobby!
        }

        run.cleanupRun(true);
    }

    private void checkForWipeCondition(RunInstance run){
        SleepyParty party = run.getParty();
        int downedPlayers = 0;

        for(UUID memberUUID : party.getMembers()){
            Player member = Bukkit.getPlayer(memberUUID);

            if(member == null || !member.isOnline() || PersistentData.has(member, SleepyKeys.IS_DOWNED.get())){
                downedPlayers++;
            }
        }

        if(downedPlayers >= party.getMembers().size()){
            for(UUID memberUUID : party.getMembers()){ // Show wiped message.
                Player member = Bukkit.getPlayer(memberUUID);

                if(member == null || !member.isOnline()) continue;
                // TODO: tp to lobby!
            }

            run.cleanupRun(false);
        }
    }
}
