package org.blackstamp.sleepychronicles.game.listener.dungeon;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
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
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

@Registrable
public class RunListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(RunManager.isNotInRun(p.getUniqueId())) return;

        p.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.MASTER, 0.15F, 0.25F));
        e.setCancelled(true);
    }

    @EventHandler
    public void onLethalDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(RunManager.isNotInRun(uuid)) return;
        if(e.getCause() == EntityDamageEvent.DamageCause.VOID) return;
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
        Set<UUID> members = party.getMembers();

        p.teleport(WorldManager.OVERWORLD.getLocation());

        ChatManager.sendMessage(p,true,"You disconnected in the midst of a run! You got redirected to the lobby.");
        party.removeMember(uuid);
        RunManager.removeRunInstance(uuid);

        if(members.isEmpty()){
            run.cleanupRun(false);
            return;
        }

        for(UUID memberUUID : party.getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) return;

            ChatManager.sendNotification(p,p.getName() + " fled early.");
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if(!(e.getEntity() instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(RunManager.isNotInRun(uuid)) return;
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())){ e.setCancelled(true); }
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
                Player memberPlayer = Bukkit.getPlayer(memberUUID);

                if(memberPlayer == null || !memberPlayer.isOnline()) continue;
                if(PersistentData.has(memberPlayer,SleepyKeys.IS_DOWNED.get())){ ReviveManager.revivePlayer(memberPlayer,run); }

                memberPlayer.teleport(WorldManager.OVERWORLD.getLocation());
                ChatManager.sendWarning(memberPlayer, "Was it a dream..?", SleepyPalette.SLEEPY.tag(2,true));
                ChatManager.sendTitle(memberPlayer, "DEFEAT", SleepyPalette.SLEEPY.tag(1,false));
            }

            run.cleanupRun(false);
        }
    }
}
