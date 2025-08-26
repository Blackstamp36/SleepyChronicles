package org.blackstamp.sleepyChronicles.listener.item;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.blackstamp.sleepyChronicles.globalClass.PickaxeMode.FORTUNE;
import static org.blackstamp.sleepyChronicles.globalClass.PickaxeMode.SILK;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

@Registrable
public class onInteract implements Listener {
    globalClass global = new globalClass();
    AtomicBoolean cooldown = new AtomicBoolean(false);

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Location l = p.getLocation();

    if((e.getAction().equals(Action.RIGHT_CLICK_AIR) && p.isSneaking()) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.isSneaking())){
        if (e.hasItem()) {
            ItemStack item = e.getItem();

            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();

                if (meta.hasCustomModelDataComponent()) {
                    globalClass.PickaxeMode mode = global.playerPickaxes.get(uuid);

                    if (mode == null) {
                        mode = globalClass.PickaxeMode.FORTUNE; // Force default
                        global.playerPickaxes.put(uuid, mode);
                        global.pickaxesCooldowns.put(uuid, false);
                    }

                    CustomModelDataComponent data = meta.getCustomModelDataComponent();

                    if(data.getStrings().contains("pale_pickaxe") && !global.pickaxesCooldowns.get(uuid)) {
                        p.sendActionBar("§7Digging mode set to §a" + global.playerPickaxes.get(uuid) + "§7!");
                        global.pickaxesCooldowns.put(uuid, true);

                        switch(mode){
                            case SILK:
                                meta.addEnchant(Enchantment.SILK_TOUCH,1,false);
                                meta.removeEnchant(Enchantment.FORTUNE);
                                item.setItemMeta(meta);
                                p.setCooldown(item,100);
                                global.playerPickaxes.put(uuid, FORTUNE);
                                break;

                            case FORTUNE:
                                meta.addEnchant(Enchantment.FORTUNE,3,false);
                                meta.removeEnchant(Enchantment.SILK_TOUCH);
                                item.setItemMeta(meta);
                                p.setCooldown(item,100);
                                global.playerPickaxes.put(uuid, SILK);
                                break;
                        }

                        p.playSound(l, Sound.BLOCK_ENCHANTMENT_TABLE_USE,1F,0.5F);
                        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () ->
                                global.pickaxesCooldowns.put(uuid, false), 100);

                        }
                    }
                }
            }
        }
    }
}
