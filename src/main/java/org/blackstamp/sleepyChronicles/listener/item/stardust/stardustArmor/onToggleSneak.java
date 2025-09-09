package org.blackstamp.sleepyChronicles.listener.item.stardust.stardustArmor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.stardustGolem;
import org.blackstamp.sleepyChronicles.util.manager.CooldownManager;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

@Registrable
public class onToggleSneak implements Listener {

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (!e.isSneaking()) return;

        globalClass global = new globalClass();

        if(global.hasCustomArmor(p, "stardust")){
            spawnStardustGolem(p);
        }

    }

    private void spawnStardustGolem(Player p){
        if(!CooldownManager.isOnCooldown(p, "stardust_golem")){
            Location l = p.getLocation();
            Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
            ServerLevel nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
            stardustGolem entity = new stardustGolem(EntityType.ZOMBIE, nmsLevel);
            entity.setPos(vec3);
            nmsLevel.addFreshEntity(entity);

            CooldownManager.setCooldown(p, "stardust_golem", null, 120 * 1000);

            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.25F,1.25F);
            p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN,0.5F,1.5F);
            p.playSound(p.getLocation(), Sound.ENTITY_GUARDIAN_DEATH,0.5F,1.25F);

        } else CooldownManager.showCooldown(p, "stardust_golem");
    }
}
