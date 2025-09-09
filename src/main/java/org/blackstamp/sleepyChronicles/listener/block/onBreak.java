package org.blackstamp.sleepyChronicles.listener.block;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

@Registrable
public class onBreak implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Location l = e.getBlock().getLocation();
        ItemStack item = p.getInventory().getItemInMainHand();
        Damageable meta = (Damageable) item.getItemMeta();
        Random r = new Random();

        if(p.hasPotionEffect(PotionEffectType.WEAVING)){
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.75F,0.25F);
            e.setCancelled(true);
            return;
        }

        Material[] damageableTools = {
                Material.DIAMOND_AXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_PICKAXE, Material.DIAMOND_SWORD, Material.DIAMOND_HOE,
                Material.GOLDEN_AXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE, Material.GOLDEN_SWORD, Material.GOLDEN_HOE,
                Material.IRON_AXE, Material.IRON_SHOVEL, Material.IRON_PICKAXE, Material.IRON_SWORD, Material.IRON_HOE,
                Material.STONE_AXE, Material.STONE_SHOVEL, Material.STONE_PICKAXE, Material.STONE_SWORD, Material.STONE_HOE,
                Material.WOODEN_AXE, Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE, Material.WOODEN_SWORD, Material.WOODEN_HOE,
                Material.SHEARS
        };

        ArrayList<Material> usableTools = new ArrayList<>();
        usableTools.add(Material.NETHERITE_AXE);
        usableTools.add(Material.NETHERITE_SWORD);
        usableTools.add(Material.NETHERITE_PICKAXE);
        usableTools.add(Material.NETHERITE_SHOVEL);
        usableTools.add(Material.NETHERITE_HOE);

        if (p.getGameMode() == GameMode.SURVIVAL) {

            if (global.getServerDay() >= 3) {
                int explode = 90;
                if (global.getServerDay() >= 6) explode = 75;
                if (global.getServerDay() >= 9) explode = 0;

                switch (e.getBlock().getType()) {
                    case GRASS_BLOCK, DIRT, DIRT_PATH, COARSE_DIRT,
                         STONE, COBBLESTONE, DEEPSLATE, ANDESITE, DIORITE, CALCITE, GRANITE, TUFF, DRIPSTONE_BLOCK,
                         COPPER_ORE, DEEPSLATE_COPPER_ORE,
                         GOLD_ORE, DEEPSLATE_GOLD_ORE,
                         COAL_ORE, DEEPSLATE_COAL_ORE,
                         IRON_ORE, DEEPSLATE_IRON_ORE,
                         EMERALD_ORE, DEEPSLATE_EMERALD_ORE,
                         LAPIS_ORE, DEEPSLATE_LAPIS_ORE,
                         REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE,
                         DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE:
                        int chance = r.nextInt(101);
                        if (chance >= explode && !usableTools.contains(item.getType())) {
                            l.createExplosion(2);
                        }
                        break;
                }

                if (p.getWorld().hasStorm()) {
                    for (Material tool : damageableTools) {
                        if (item.getType().equals(tool)) {
                            meta.setDamage((int) (meta.getDamage() * 1.5));
                            item.setItemMeta(meta);

                        }
                    }
                }

            }
        }
    }

}
