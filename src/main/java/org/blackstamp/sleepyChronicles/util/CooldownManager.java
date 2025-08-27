package org.blackstamp.sleepyChronicles.util;

import org.blackstamp.sleepyChronicles.globalClass;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

public class CooldownManager {

    public static boolean isOnCooldown(Player p, String cooldownName) {
        Map<UUID, Map<String, Long>> cooldownMap = globalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.computeIfAbsent(p.getUniqueId(), k -> new HashMap<>());

        Long expirationTime = playerCooldowns.get(cooldownName);
        return expirationTime != null && System.currentTimeMillis() < expirationTime;
    }

    public static void setCooldown(Player p, String cooldownName, ItemStack item, long cooldownTimeMs) {
        Map<UUID, Map<String, Long>> cooldownMap = globalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.computeIfAbsent(p.getUniqueId(), k -> new HashMap<>());
        long expirationTime = System.currentTimeMillis() + cooldownTimeMs;
        playerCooldowns.put(cooldownName, expirationTime);

        p.sendMessage(PREFIX + "§aYou used your " + item.getItemMeta().getDisplayName() + " §atrinket!");
        p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH,0.35F,0.75F);
        p.playSound(p.getLocation(), Sound.BLOCK_TRIAL_SPAWNER_BREAK,0.75F,1.25F);
        p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER,0.35F,0.75F);
        p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH,0.35F,0.75F);
        p.playSound(p.getLocation(), Sound.ITEM_MACE_SMASH_GROUND,0.35F,0.75F);
    }

    public static long getRemainingCooldown(Player p, String cooldownName) {
        Map<UUID, Map<String, Long>> cooldownMap = globalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.get(p.getUniqueId());
        if (playerCooldowns == null) return 0;

        Long expirationTime = playerCooldowns.get(cooldownName);
        if (expirationTime == null) return 0;

        long remaining = expirationTime - System.currentTimeMillis();
        return Math.max(0, remaining);
    }

    public static void showTrinketCooldown(Player p, String cooldownName){
        long remainingMs = CooldownManager.getRemainingCooldown(p, cooldownName);
        long remainingSeconds = remainingMs / 1000;
        long remainingMinutes = remainingSeconds / 60;
        long showableSeconds = (remainingMs / 1000) % 60;

        p.sendMessage(PREFIX + "§cYour trinket is on cooldown! (" + remainingMinutes + "m " + showableSeconds + "s)");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.15F,0.75F);

    }
}