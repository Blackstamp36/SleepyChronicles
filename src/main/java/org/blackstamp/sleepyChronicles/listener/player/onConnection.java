package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.listener.environment.onWeather;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

@Registrable
public class onConnection implements Listener {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    onWeather weather = new onWeather();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        globalClass global = new globalClass();

        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        global.createPlayerData(uuid);

            if(!p.hasPlayedBefore()){
                scoreboard.getTeam("player").addPlayer(p);
            }

        e.setJoinMessage("§6※ §a" + p.getName() + " §7has logged in!");
        global.cancelFallDamage.put(uuid, false);
        global.pickaxesCooldowns.put(uuid, false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        e.setQuitMessage("§6※ §c" + p.getName() + " §7has disconnected!");
    }

}
