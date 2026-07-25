package org.blackstamp.sleepychronicles.api.cooldown;

import lombok.Getter;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    @Getter private static CooldownManager instance;

    private final Map<UUID, HashMap<String, Long>> cooldownMap;

    public CooldownManager(){
        instance = this;
        cooldownMap = new HashMap<>();
    }

    public void setCooldown(UUID uuid, String name, long millis){
        cooldownMap.computeIfAbsent(uuid, c -> new HashMap<>()).put(name, System.currentTimeMillis() + millis);
    }

    public int getRemainingTime(UUID uuid, String name){
        if(!hasCooldown(uuid, name)) return 0;

        final long remaining = cooldownMap.get(uuid).get(name) - System.currentTimeMillis();

        return Math.max(0,(int) (remaining / ConstantFields.ONE_SECOND));
    }

    public boolean hasCooldown(UUID uuid, String name){
        if(!cooldownMap.containsKey(uuid)) return false;

        Map<String, Long> playerCooldowns = cooldownMap.get(uuid);

        if(!playerCooldowns.containsKey(name)) return false;

        return playerCooldowns.get(name) >= System.currentTimeMillis();
    }

    public void clearData(UUID uuid){ cooldownMap.remove(uuid); }

    private class CacheListener implements Listener {

        @EventHandler
        public void ClearCache(PlayerQuitEvent e){
            Player p = e.getPlayer();

            clearData(p.getUniqueId());
        }
    }
}