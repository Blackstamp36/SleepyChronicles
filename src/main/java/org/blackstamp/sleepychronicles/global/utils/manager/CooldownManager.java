package org.blackstamp.sleepychronicles.global.utils.manager;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

public class CooldownManager {

    public static boolean isOnCooldown(Player p, String cooldownName) {
        // Checks whether an item or ability is on cooldown.
        // The cooldown must be added on the HashMap for it to check first.

        Map<UUID, Map<String, Long>> cooldownMap = GlobalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.computeIfAbsent(p.getUniqueId(), k -> new HashMap<>());

        Long expirationTime = playerCooldowns.get(cooldownName);
        return expirationTime != null && System.currentTimeMillis() < expirationTime;
    }

    public static void setCooldown(Player p, String cooldownName, @Nullable ItemStack item, long cooldownTimeMs) {
        // Adds a cooldown key into the HashMap. Preferably with no capital letters.

        Map<UUID, Map<String, Long>> cooldownMap = GlobalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.computeIfAbsent(p.getUniqueId(), _ -> new HashMap<>());
        long expirationTime = System.currentTimeMillis() + cooldownTimeMs;
        playerCooldowns.put(cooldownName, expirationTime);

        if(item == null) return;

        p.setCooldown(item,(int) (cooldownTimeMs / 1000) * 20);
        p.sendMessage(chatPrefix + "§aYou used your " + item.getItemMeta().getDisplayName() + "§a!");
        p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 0.15F, 0.75F);
        p.playSound(p.getLocation(), Sound.BLOCK_TRIAL_SPAWNER_BREAK, 0.15F, 1.25F);
        p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.15F, 0.75F);
        p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 0.15F, 0.75F);
        p.playSound(p.getLocation(), Sound.ITEM_MACE_SMASH_GROUND, 0.15F, 0.75F);
    }

    public static long getRemainingCooldown(Player p, String cooldownName) {
        // Gets the remaining time of a specific cooldown. In case it has been added of course.

        Map<UUID, Map<String, Long>> cooldownMap = GlobalClass.getActiveCooldowns();

        Map<String, Long> playerCooldowns = cooldownMap.get(p.getUniqueId());
        if (playerCooldowns == null) return 0;

        Long expirationTime = playerCooldowns.get(cooldownName);
        if (expirationTime == null) return 0;

        long remaining = expirationTime - System.currentTimeMillis();
        return Math.max(0, remaining);
    }

    public static void showCooldown(Player p, String cooldownName){
        // Shows the remaining time through a chat message to a Player.

        long remainingMs = CooldownManager.getRemainingCooldown(p, cooldownName);
        long remainingSeconds = remainingMs / 1000;
        long remainingMinutes = remainingSeconds / 60;
        long showableSeconds = (remainingMs / 1000) % 60;

        p.sendMessage(chatPrefix + "§cYour utility is on cooldown! (" + remainingMinutes + "m " + showableSeconds + "s)");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.15F,0.75F);

    }
}