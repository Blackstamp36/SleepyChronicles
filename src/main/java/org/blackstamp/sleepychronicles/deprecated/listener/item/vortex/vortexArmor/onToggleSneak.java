package org.blackstamp.sleepychronicles.deprecated.listener.item.vortex.vortexArmor;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

import static org.blackstamp.sleepychronicles.global.GlobalClass.cancelFallDamage;

@Registrable
public class onToggleSneak implements Listener {
    private final int particleCount = 16;

    Vector doubleJump = new Vector(0,0.75,0);

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        ParticleManager pM = new ParticleManager(p.getWorld());
        UUID uuid = p.getUniqueId();

        if (!e.isSneaking()) {
            return;
        }

        GlobalClass global = new GlobalClass();

        if(global.hasCustomArmor(p, "vortex")){
            if(!CooldownManager.isOnCooldown(p, "vortex_jump")){
                CooldownManager.setCooldown(p, "vortex_jump", null, 15 * 1000);
                cancelFallDamage.put(uuid, true);
                p.setVelocity(doubleJump);
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,0.25F,1.25F);
                pM.spawnParticle(p.getLocation(), Particle.SMALL_GUST, null,
                        particleCount,0.25,0.5,0.25,0.25);
            } else {
                CooldownManager.showCooldown(p, "vortex_jump");
            }
        }

    }
}
