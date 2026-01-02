package org.blackstamp.sleepychronicles.deprecated.listener.item.misc.mechanicalEye;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Registrable
public class onInteract implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack itemInteracted = e.getItem();

        if(itemInteracted == null) return;
        if(!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if(!(global.isCustomItem(itemInteracted, "mechanical_eye"))) return;

        showSummonActionBar(p, itemInteracted);

        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () ->
                spawnMechanicalEyeAt(p), 200);
            }

        private void spawnMechanicalEyeAt(Player p){
            Location l = p.getLocation();
            Vec3 vec3 = new Vec3(l.getX(), l.getY() + 5, l.getZ());
            Level nmsLevel = ((CraftWorld) p.getWorld()).getHandle();
            mechanicalEye entity = new mechanicalEye(EntityType.WITHER, nmsLevel);
            nmsLevel.addFreshEntity(entity);
            entity.setPos(vec3);
        }

        private void showSummonActionBar(Player p, ItemStack summoningItem){
            Location l = p.getLocation();
            ParticleManager pM = new ParticleManager(p.getWorld());

            pM.spawnParticle(l, Particle.ENCHANT, null,
                    25,0.5,0.25,0.5,1.0);
            p.playSound(l, Sound.BLOCK_NOTE_BLOCK_PLING,0.85F,0.15F);
            p.sendActionBar(ChatColor.of("#5dea7a") + "You feel an evil presence watching you...");
            summoningItem.subtract();
        }
    }


