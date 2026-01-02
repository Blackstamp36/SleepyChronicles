package org.blackstamp.sleepychronicles.deprecated.listener.block.paleOre;

import org.blackstamp.sleepychronicles.deprecated.items.pale.paleItems;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

@Registrable
public class onBreak implements Listener {
    paleItems paleItems = new paleItems();

    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        Random r = new Random();
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location l = e.getBlock().getLocation();

        if (p.getWorld().getName().equals("world_aftermath") && p.getGameMode().equals(GameMode.SURVIVAL)) {
            ItemStack main = p.getInventory().getItemInMainHand();
            ItemStack shards = paleItems.createPaleShard();

            if (b.getType().equals(Material.CHISELED_RESIN_BRICKS)) {
                if (main.hasItemMeta()) {
                    ItemMeta meta = main.getItemMeta();
                    if (meta.hasEnchant(Enchantment.FORTUNE)) {
                        shards.setAmount(r.nextInt(1, meta.getEnchantLevel(Enchantment.FORTUNE)));
                        p.sendActionBar(ChatColor.of("#cfc4c3") + "You feel lucky!");
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2F);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 0.75F, 1.25F);
                    }
                }

                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,0.75F,1.25F);
                e.setCancelled(true);
                b.setType(Material.AIR);
                l.getWorld().dropItemNaturally(l, shards);
            }
        }
    }
}
