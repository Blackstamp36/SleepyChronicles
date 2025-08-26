package org.blackstamp.sleepyChronicles.listener.item.eyePearl;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

@Registrable
public class onPearl implements Listener {

    @EventHandler
    private void onPearl(ProjectileHitEvent e) {
        if (e.getEntity() instanceof EnderPearl pearl) {

            if (pearl.getShooter() instanceof Player p) {
                ItemStack pearlItem = pearl.getItem();

                if (pearlItem.hasItemMeta()) {
                    ItemMeta pearlMeta = pearlItem.getItemMeta();
                    CustomModelDataComponent pearlData = pearlMeta.getCustomModelDataComponent();

                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 0);

                    if (pearlData.getStrings().contains("eye_pearl")) {
                        BukkitTask task;

                        task = Bukkit.getScheduler().runTaskTimer(sleepyChronicles.getter(), () ->
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 0), 1,20);

                        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                            task.cancel();

                            Location prevL = pearl.getOrigin();
                            if(prevL == null) return;

                            prevL.setY(pearl.getOrigin().getBlockY() - 1);
                            prevL.setYaw(p.getYaw());
                            p.teleport(prevL);
                            p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 1, 0.5F);

                        }, 100);
                    }
                }
            }
        }
    }
}
