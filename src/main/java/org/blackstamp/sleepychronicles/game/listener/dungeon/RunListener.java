package org.blackstamp.sleepychronicles.game.listener.dungeon;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
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
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

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
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if(!RunManager.isInRun(p.getUniqueId())) return;

        SleepyParty party = PartyManager.getParty(uuid);

        this.applyDowned(p);
        this.checkForWipeCondition(party);

        e.setCancelled(true);
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e){
        Entity boss = e.getEntity();
        UUID bossUUID = boss.getUniqueId();
        RunInstance run = RunManager.getBoss(bossUUID);

        if(run == null) return;

        // If we reach here, it means that the run is active and valid!
        SleepyParty party = run.getParty();


    }

    private void showVictory(){

    }

    private void applyDowned(Player p){ // Execute downed logic..
        if(PersistentData.has(p, SleepyKeys.DOWNED)) return;

        PersistentData.set(p,SleepyKeys.DOWNED, PersistentDataType.BYTE,(byte) 1);

        ChatManager.sendWarning(p,"You've been downed!",null);

        p.setPose(Pose.SLEEPING);
    }

    private void checkForWipeCondition(SleepyParty party){
        int downedPlayers = 0;

        for(UUID memberUUID : party.getMembers()){
            Player member = Bukkit.getPlayer(memberUUID);

            if(member == null || !member.isOnline() || PersistentData.has(member, SleepyKeys.DOWNED)){
                downedPlayers++;
            }
        }

        if(downedPlayers >= party.getMembers().size()){ // Do wiped logic.
            for(UUID memberUUID : party.getMembers()){
                Player member = Bukkit.getPlayer(memberUUID);

                if(member == null || !member.isOnline()) continue;


            }

        }
    }
}
