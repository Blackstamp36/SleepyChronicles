package org.blackstamp.sleepychronicles.deprecated.listener.item.parry;

import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

import static org.blackstamp.sleepychronicles.global.GlobalClass.playerParrys;

@Registrable
public class onDamageToP implements Listener {
    private final int particleCount = 100;

    @EventHandler
    private void onDamageToP(EntityDamageEvent e){
        Entity entity = e.getEntity();
        Location l = entity.getLocation();
        ParticleManager pM = new ParticleManager(entity.getWorld());

        boolean parryRequirements = e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                || e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);

        if(!(entity instanceof Player p)) return;
        if(!parryRequirements) return;

        UUID uuid = p.getUniqueId();

        if(!playerParrys.containsKey(uuid)) return;

        if(playerParrys.get(uuid)){
            p.playSound(l, Sound.BLOCK_NOTE_BLOCK_PLING,0.85F,1.25F);
            p.playSound(l, Sound.ITEM_MACE_SMASH_GROUND_HEAVY,0.85F,0.75F);
            p.playSound(l, Sound.ENTITY_ELDER_GUARDIAN_DEATH,0.45F,1.75F);
            pM.spawnParticle(l, Particle.HAPPY_VILLAGER,null,
                    particleCount,0.5,1.0,0.5,1.0);
            pM.spawnParticle(l, Particle.TOTEM_OF_UNDYING,null,
                    particleCount,0.5,1.0,0.5,1.0);
            p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,2,0,false,false));
            playerParrys.put(uuid, false);
        }

    }
}
