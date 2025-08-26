package org.blackstamp.sleepyChronicles.listener.item.mechanicalEye;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

@Registrable
public class onInteract implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Location l = p.getLocation();

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = e.getItem();
                if (item != null && item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();

                    if (meta != null && meta.hasCustomModelDataComponent()) {
                        CustomModelDataComponent data = meta.getCustomModelDataComponent();
                        if (data.getStrings().contains("mechanical_eye")) {
                            global.spawnParticles(l, Particle.ENCHANT, null, 100);
                            p.playSound(l, Sound.BLOCK_NOTE_BLOCK_PLING,1,0.5F);
                            p.sendActionBar(ChatColor.of("#5dea7a") + "You feel an evil presence watching you...");
                            item.subtract();
                            Location spawn = new Location(l.getWorld(), l.getX(), l.getY() + 5, l.getZ());

                            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () ->
                                    mechanicalEye.spawnEntity(spawn, 1), 200);
                        }
                    }
                }
            }
        }
    }


