package org.blackstamp.sleepyChronicles.listener.entity.creaking.bobCreaking;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creaking.bobCreaking;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

@Registrable
public class onDamageToE implements Listener {
    ArrayList<Material> usableTools = getUsableTools();

    @EventHandler(priority = EventPriority.LOWEST)
    private void onDamageToE(EntityDamageEvent e) {
        globalClass global = new globalClass();
        Entity entity = e.getEntity();
        Entity damager = e.getDamageSource().getDirectEntity();

        if(!(entity instanceof Creaking)) return;
        if (!(entity instanceof CraftEntity craftEntity)) return;
        net.minecraft.world.entity.Entity nmsEntity = craftEntity.getHandle();
        if(!(nmsEntity instanceof bobCreaking)) return;

        if(!(damager instanceof Player p)) return;

        Material main = p.getInventory().getItemInMainHand().getType();

        if (!usableTools.contains(main)) {
            global.spawnParticles(entity.getLocation(), Particle.BLOCK, Material.PALE_OAK_LOG, 10);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
            e.setCancelled(true);
        }
    }

    private ArrayList<Material> getUsableTools(){
        ArrayList<Material> usableTools = new ArrayList<>();
        usableTools.add(Material.NETHERITE_AXE);
        usableTools.add(Material.DIAMOND_AXE);
        usableTools.add(Material.GOLDEN_AXE);
        usableTools.add(Material.IRON_AXE);
        usableTools.add(Material.STONE_AXE);
        usableTools.add(Material.WOODEN_AXE);

        return usableTools;
    }
}
