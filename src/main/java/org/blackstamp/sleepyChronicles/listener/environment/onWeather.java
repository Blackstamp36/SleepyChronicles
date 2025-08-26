package org.blackstamp.sleepyChronicles.listener.environment;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.serverDay;

@Registrable
public class onWeather implements Listener {
    private static final String BAR_TITLE = "§6Dᴏᴏᴍɪꜱʜ Eʀᴀ §8| §7";

    private BukkitTask stormTask;
    private final Map<UUID, BossBar> playerBossBars = new HashMap<>();
    public boolean isStormActive = false;
    public int remainingSeconds = 0;
    private int storedSeconds = 0;
    public int stormMins = 15;

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (!e.toWeatherState()) {
            cleanupStorm();
            return;
        }
            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                if (e.getWorld().hasStorm()) {
                    startStorm(e.getWorld());
                }
            }, 1);

    }

    private void startStorm(World w) {
        cleanupStorm();

        stormMins = serverDay * 15;
        isStormActive = true;
        storedSeconds += calculateInitialDuration(0, stormMins, 1);
        remainingSeconds = storedSeconds;

        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendTitle("§e§k| §f§6Dᴏᴏᴍɪꜱʜ Eʀᴀ §e§k|", "§7With a duration of " + stormMins + "m!");
            all.playSound(all, Sound.BLOCK_END_PORTAL_SPAWN, 1, 0F);
            showBar(all);
        });

        stormTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isStormActive || remainingSeconds <= 0) {
                    cleanupStorm();
                    return;
                }

                w.setTime(16000);
                remainingSeconds--;
                storedSeconds = remainingSeconds;
                updateBossBars(remainingSeconds);

                Bukkit.getWorlds().forEach(world -> world.setWeatherDuration(100));

                if (remainingSeconds <= 0) {
                    endStormNormally();
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 20);
    }

    private void cleanupStorm() {
        if (stormTask != null) {
            stormTask.cancel();
            stormTask = null;
        }

            playerBossBars.forEach((uuid, bar) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    player.hideBossBar(bar);
                }
            });
            playerBossBars.clear();

            remainingSeconds = 0;
    }

    private int calculateInitialDuration(int hours, int minutes, int seconds) {
        return hours * 3600 + minutes * 60 + seconds;
    }

    public void showBar(Player p) {
        BossBar bar = BossBar.bossBar(
                Component.text(BAR_TITLE + formatTime(remainingSeconds)),
                1.0f,
                BossBar.Color.YELLOW,
                BossBar.Overlay.PROGRESS
        );
        playerBossBars.put(p.getUniqueId(), bar);
        p.showBossBar(bar);

        updateBossBars(remainingSeconds);
    }

    public void hideBar(Player p) {
        UUID uuid = p.getUniqueId();
            if(playerBossBars.get(uuid) != null) {
                System.out.println("BOSSBAR HID!");
                p.hideBossBar(playerBossBars.get(uuid));
                playerBossBars.remove(uuid);
            }
    }

    public void updateBossBars(int remainingSeconds) {
        String timeText = formatTime(remainingSeconds);

        for (Map.Entry<UUID, BossBar> entry : playerBossBars.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null && player.isOnline()) {
                entry.getValue().name(Component.text(BAR_TITLE + timeText));
            }
        }
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void endStormNormally() {
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendMessage("§8| §6The storm has ended abruptly!");
            all.getWorld().setStorm(false);
            storedSeconds = 0;
            cleanupStorm();
        });

        isStormActive = false;
        }

}
