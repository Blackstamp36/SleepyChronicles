package org.blackstamp.sleepychronicles.game.listener.dungeon;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.ReviveManager;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.blackstamp.sleepychronicles.game.world.dimensions.WorldManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@Registrable
public class RunListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(RunManager.isNotInRun(p.getUniqueId())) return;

        p.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 1.0F, 0.25F));
        e.setCancelled(true);
    }

    @EventHandler
    public void onLethalDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(RunManager.isNotInRun(uuid)) return;
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())){
            e.setCancelled(true);
            return;
        }

        boolean isLethal = (p.getHealth() - e.getFinalDamage()) <= 0;

        if(!isLethal) return;

        e.setCancelled(true);
        p.setHealth(1.0D);

        RunInstance run = RunManager.getRunInstance(uuid);

        ReviveManager.setDowned(p,run);
        this.checkForWipeCondition(run);
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e){
        Player p = e.getPlayer();

        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTarget(EntityTargetEvent e){
        if(!(e.getTarget() instanceof Player p)) return;

        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())){ e.setCancelled(true); }
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

        SleepyParty party = run.getParty();

        party.removeMember(uuid);
        RunManager.removeRunInstance(uuid);

        if(party.getMembers().isEmpty()){ run.cleanupRun(false); }
    }

    // Manager methods.

    private void showVictory(RunInstance run){
        SleepyParty party = run.getParty();

        for(UUID memberUUID : party.getMembers()){
            Player member = Bukkit.getPlayer(memberUUID);

            if(member == null || !member.isOnline()) continue;

            if(PersistentData.has(member,SleepyKeys.IS_DOWNED.get())){ ReviveManager.revivePlayer(member,run); }

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
                if(PersistentData.has(member,SleepyKeys.IS_DOWNED.get())){ ReviveManager.revivePlayer(member,run); }

                member.teleport(WorldManager.OVERWORLD.getLocation());
                ChatManager.sendMessage(member,false,"Suddenly, you seem to awake in the middle of confusion. Was it a dream..?");
            }

            run.cleanupRun(false);
        }
    }
}
