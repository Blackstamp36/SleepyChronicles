package org.blackstamp.sleepyChronicles.listener.item.vortex.vortexShortbow;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.CooldownManager;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

@Registrable
public class onInteract implements Listener {
    globalClass global = new globalClass();
    private final Random r = new Random();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inventory = p.getInventory();
        ItemStack main = inventory.getItemInMainHand();

// These if-cases check that the player is holding the right item.
        if (!e.getAction().toString().contains("LEFT_CLICK")) return;
        if (!global.isCustomItem(main, "vortex_shortbow")) return;
        if (CooldownManager.isOnCooldown(p, "vortex_shortbow")) return;
        if (!global.hasCustomArmor(p, "vortex")) return;
        if (!p.getInventory().contains(Material.ARROW)) return;

        ItemMeta mainMeta = main.getItemMeta();
        double arrowDamage = 2.0; // Base arrow damage.
        int cooldownMS = 250; // Cooldown between shots to prevent from autoclicking.

                CooldownManager.setCooldown(p, "vortex_shortbow", null, cooldownMS);
                ItemStack arrowToSubstract = new ItemStack(Material.ARROW);
                arrowToSubstract.setAmount(1);
                Arrow arrow = p.launchProjectile(Arrow.class);

                if (mainMeta.hasEnchant(Enchantment.POWER)) arrowDamage += mainMeta.getEnchantLevel(Enchantment.POWER);

                int fireTicks = 60; // 3s of Fire ticks.
                if (mainMeta.hasEnchant(Enchantment.FLAME) && !p.isInRain()) arrow.setFireTicks(fireTicks);

                int critChance = 15; // 15% chance of being a critic arrow.
                if (r.nextInt(0, 101) <= critChance) arrow.setCritical(true);

                arrow.setDamage(arrowDamage); // After all the modifications have been done, it applies them into the arrow.
                p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_SHOOT, 0.85F, 1.25F);

                if(p.getGameMode().equals(GameMode.SURVIVAL)) inventory.removeItemAnySlot(arrowToSubstract);
                // Removes one arrow from the player's inventory.

        }
}

