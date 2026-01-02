package org.blackstamp.sleepychronicles.deprecated.listener.item.pale.paleAxe;

import org.blackstamp.sleepychronicles.deprecated.items.pale.paleItems;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.HashMap;
import java.util.Random;

@Registrable
public class onBreak implements Listener {
    paleItems paleItems = new paleItems();
    Random r = new Random();
    private final HashMap<Location, Block> placedBlocks = new HashMap<>();

    @EventHandler
    private void onBreak(BlockBreakEvent e){
        int chance = 5;
        Player p = e.getPlayer();

        Block b = e.getBlock();
        Location l = b.getLocation();
        ItemStack main = p.getInventory().getItemInMainHand();

        if(main != null && main.hasItemMeta()){
            ItemMeta meta = main.getItemMeta();

        if (meta != null && meta.hasCustomModelDataComponent()) {
            CustomModelDataComponent data = meta.getCustomModelDataComponent();
            if (data.getStrings().contains("pale_axe") && b.getType().equals(Material.PALE_OAK_LOG) && !placedBlocks.containsKey(l)) {
                ItemStack apples = paleItems.createLividApple();
                apples.setAmount(r.nextInt(1,4));

                if(meta.hasEnchant(Enchantment.FORTUNE)){
                    chance += 5 * meta.getEnchantLevel(Enchantment.FORTUNE);
                    apples.setAmount(apples.getAmount() + meta.getEnchantLevel(Enchantment.FORTUNE));
                }

                if(r.nextInt(0,101) <= chance) {
                    p.sendActionBar(ChatColor.of("#cfc4c3") + "You feel lucky!");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2F);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.75F, 1.25F);
                    l.getWorld().dropItemNaturally(l, apples);

                    }
                }
            }
        }

        placedBlocks.remove(l);
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent e){
        Block b = e.getBlock();
        Location l = b.getLocation();

        placedBlocks.put(l, b);
    }
}
