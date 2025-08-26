package org.blackstamp.sleepyChronicles.listener.entity.creaking.bobCreaking;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

@Registrable
public class onDamageToE implements Listener {

    @EventHandler
    private void onDamageToE(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getDirectEntity();

        ArrayList<Material> usableTools = new ArrayList<>();
        usableTools.add(Material.NETHERITE_AXE);
        usableTools.add(Material.DIAMOND_AXE);
        usableTools.add(Material.GOLDEN_AXE);
        usableTools.add(Material.IRON_AXE);
        usableTools.add(Material.STONE_AXE);
        usableTools.add(Material.WOODEN_AXE);

            if (entity instanceof Creaking && entity.getScoreboardTags().contains("bobCreaking")) {
                if(damager instanceof Monster
                        || e.getDamageSource().getDamageType().equals(DamageType.ARROW)
                        || e.getDamageSource().getDamageType().equals(DamageType.MOB_PROJECTILE)){
                    e.setCancelled(true);
                } else if (damager instanceof Player p) {

                Material main = p.getInventory().getItemInMainHand().getType();

                if (usableTools.contains(main)) {
                    global.spawnParticles(entity.getLocation(), Particle.BLOCK, Material.CREAKING_HEART, 50);
                    p.playSound(p.getLocation(), Sound.BLOCK_CREAKING_HEART_BREAK, 0.75F, 0.5F);
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.5F, 0.75F);

                } else {
                    global.spawnParticles(entity.getLocation(), Particle.BLOCK, Material.PALE_OAK_LOG, 10);
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    e.setCancelled(true);
                }
            }
        }
    }
}
