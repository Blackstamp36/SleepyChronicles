package org.blackstamp.sleepychronicles.game.listener.dungeon;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.blackstamp.sleepychronicles.api.player.PlayerManager;
import org.blackstamp.sleepychronicles.game.mobs.custom.misc.ReviveStand;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@Registrable
public class RunListener implements Listener {
    private static final PotionEffect[] downedDebuff = {
            new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,4),
            new PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0),
            new PotionEffect(PotionEffectType.GLOWING,PotionEffect.INFINITE_DURATION,0)
    };

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

        if(!RunManager.isInRun(p.getUniqueId())) return;

        boolean isLethal = (p.getHealth() - e.getFinalDamage()) <= 0;

        if(!isLethal) return;

        p.setHealth(1.0D);

        // So we don't 'kill' a downed player AGAIN.
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())) return;

        SleepyParty party = PartyManager.getParty(uuid);
        RunInstance run = RunManager.getRun(uuid);

        this.setDowned(p,run);
        this.checkForWipeCondition(party,run);

        e.setCancelled(true);
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e){
        Entity boss = e.getEntity();
        UUID bossUUID = boss.getUniqueId();
        RunInstance run = RunManager.getBossInstance(bossUUID);

        if(run == null) return;

        // If we reach here, it means that the run is active and valid!
        SleepyParty party = run.getParty();

        this.showVictory(party);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRun(uuid);

        if(run == null) return;

        // Check also if the entire party is empty.
        // If it is, then remove all remains of the run so it doesn't consume memory.
    }

    // Manager methods.

    private void setDowned(Player p, RunInstance run){ // Execute downed logic..
        if(PersistentData.has(p, SleepyKeys.IS_DOWNED.get())) return;

        PersistentData.set(p, SleepyKeys.IS_DOWNED.get(), PersistentDataType.BYTE,(byte) 1);

        UUID uuid = p.getUniqueId();
        Level level = ((CraftWorld) p.getLocation().getWorld()).getHandle();

        run.increaseDownedCount(uuid);

        ReviveStand reviveStand = new ReviveStand(level,run,uuid);

        level.addFreshEntity(reviveStand, CreatureSpawnEvent.SpawnReason.CUSTOM);

        ChatManager.sendWarning(p,"You've been downed!",null);
        p.setPose(Pose.SLEEPING);
        PlayerManager.addPots(p, downedDebuff);
    }

    private void showVictory(SleepyParty party){ // Execute victory logic..

    }

    private void checkForWipeCondition(SleepyParty party, RunInstance run){
        int downedPlayers = 0;

        for(UUID memberUUID : party.getMembers()){
            Player member = Bukkit.getPlayer(memberUUID);

            if(member == null || !member.isOnline() || PersistentData.has(member, SleepyKeys.IS_DOWNED.get())){
                downedPlayers++;
            }
        }

        if(downedPlayers >= party.getMembers().size()){ // Do wiped logic.
            for(UUID memberUUID : party.getMembers()){
                Player member = Bukkit.getPlayer(memberUUID);

                if(member == null || !member.isOnline()) continue;


            }

            // Maybe to get the boss, I can register its UUID on the RunInstance? And then I get it from there?
        }
    }
}
