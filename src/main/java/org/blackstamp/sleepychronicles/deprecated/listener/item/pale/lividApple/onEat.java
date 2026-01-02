package org.blackstamp.sleepychronicles.deprecated.listener.item.pale.lividApple;

import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Registrable
public class onEat implements Listener {

    @EventHandler
    private void onEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack food = e.getItem();

        if (food.hasItemMeta()) {
            ItemMeta meta = food.getItemMeta();

            if (meta != null && meta.hasCustomModelDataComponent()) {
                CustomModelDataComponent data = meta.getCustomModelDataComponent();
                if (data.getStrings().contains("livid_apple")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1,1.5F);
                    p.playSound(p.getLocation(), Sound.BLOCK_TRIAL_SPAWNER_OMINOUS_ACTIVATE,0.75F,1.25F);


                }
            }
        }
    }
}
