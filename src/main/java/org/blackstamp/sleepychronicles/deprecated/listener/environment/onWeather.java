package org.blackstamp.sleepychronicles.deprecated.listener.environment;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;
import static org.blackstamp.sleepychronicles.SleepyChronicles.serverDay;

@Registrable
public class onWeather implements Listener {
    private static final String BAR_TITLE = ChatColor.of("#cfc4c3") + "Uɴɪᴠᴇʀꜱᴀʟ Cᴏʟʟᴀᴘꜱᴇ §8| §7";

    private final AtomicReference<BukkitTask> stormTask = new AtomicReference<>();
    private final Map<UUID, BossBar> playerBossBars = new HashMap<>();
    public static boolean isStormActive = false;
    private int remainingSeconds = 0;
    private int storedSeconds = 0;
    private int stormMins = 15;

    private static final int STORM_TIME = 16000;
    private static final int WEATHER_DURATION = 100;
    private static final int TICKS_PER_SECOND = 20;

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        World world = e.getWorld();
        if (!e.toWeatherState()) {
            cleanupStorm();
            return;
        }

        if(e.getCause().equals(WeatherChangeEvent.Cause.PLUGIN)) {
            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
                if(world.hasStorm())
                    startStorm(world);
            }, 1);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(isStormActive){
            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () ->
                    showBar(e.getPlayer()),10);
        }
    }


    public void startStorm(World w) {
        cleanupStorm();

        stormMins = serverDay * 15;
        isStormActive = true;
        storedSeconds += calculateInitialDuration(0, stormMins, 1);
        remainingSeconds = storedSeconds;

        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendTitle("§e§k| §f" + ChatColor.of("#cfc4c3") + "Uɴɪᴠᴇʀꜱᴀʟ Cᴏʟʟᴀᴘꜱᴇ §e§k|",
                    "§7With a duration of " + stormMins + "m!");
            all.playSound(all, Sound.BLOCK_END_PORTAL_SPAWN, 1, 0.5F);
            all.playSound(all, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.25F);
            showBar(all);
        });

        BukkitTask existingTask = stormTask.get();
        if (existingTask != null) {
            existingTask.cancel();
        }

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {

                if (!isStormActive || remainingSeconds <= 0) {
                    cleanupStorm();
                    return;
                }

                w.setTime(STORM_TIME);
                remainingSeconds--;
                storedSeconds = remainingSeconds;

                Bukkit.getWorlds().forEach(world -> world.setWeatherDuration(WEATHER_DURATION));
                updateBossBars(remainingSeconds);

                if (remainingSeconds <= 0) {
                    endStormNormally();
                }

            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, TICKS_PER_SECOND);

        stormTask.set(task);
    }

    public void cleanupStorm() {
        BukkitTask task = stormTask.getAndSet(null);
        if (task != null) {
            task.cancel();
        }

            playerBossBars.forEach((uuid, bar) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    player.hideBossBar(bar);
                }
            });
            playerBossBars.clear();

            remainingSeconds = 0;
            isStormActive = false;
    }

    private int calculateInitialDuration(int hours, int minutes, int seconds) {
        return hours * 3600 + minutes * 60 + seconds;
    }

    public void showBar(Player p) {
        hideBar(p);

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
        BossBar bar = playerBossBars.remove(uuid);
        if (bar != null) {
            p.hideBossBar(bar);
        }
    }

    public void updateBossBars(int remainingSeconds) {
        String timeText = formatTime(remainingSeconds);

        for (Map.Entry<UUID, BossBar> entry : playerBossBars.entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p != null && p.isOnline()) {
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

    public void endStormNormally() {
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendMessage(chatPrefix + "§6The storm has ended!");
            all.playSound(all.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH,1,1.25F);
            all.getWorld().setStorm(false);
        });

        storedSeconds = 0;
        remainingSeconds = 0;
        cleanupStorm();
        }
}
