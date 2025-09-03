package org.blackstamp.sleepyChronicles.listener.entity;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.paleSoul;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.Random;

@Registrable
public class onEntityDeath implements Listener {

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        Random r = new Random();
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();
        globalClass global = new globalClass();

        if(entity.getKiller() != null && entity instanceof Monster) {
            Player p = entity.getKiller();
            ItemStack item = p.getInventory().getItemInMainHand();

            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();

                if (meta.hasCustomModelDataComponent()) {
                    CustomModelDataComponent data = meta.getCustomModelDataComponent();
                    if (data.getStrings().contains("pale_sword")) {
                        int normalChance = 5;
                        int paleChance = 50;

                        if(meta.hasEnchant(Enchantment.LOOTING)){
                            normalChance += 5 * meta.getEnchantLevel(Enchantment.LOOTING);
                            paleChance += 50 * meta.getEnchantLevel(Enchantment.LOOTING);
                        }

                        if ((l.getWorld().getBiome(l).toString().contains("PALE") && r.nextInt(1,1001) <= paleChance)
                                || (!(l.getWorld().getBiome(l).toString().contains("PALE")) && r.nextInt(1,1001) <= normalChance)){
                            paleSoul.spawnEntity(l, 1, entity.getKiller());

                            global.spawnParticles(l, Particle.ENCHANT,null,25);
                        }
                    }

                }
            }
        }

    }
}

