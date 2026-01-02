package org.blackstamp.sleepychronicles.deprecated.listener.item.parry;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static org.blackstamp.sleepychronicles.global.GlobalClass.playerParrys;

@Registrable
public class onInteract implements Listener {
    private final int maxParryTicks = 5;
    private final int particleCount = 75;

    @EventHandler
    private void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack main = e.getItem();

        if(main == null) return;
        if(!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if(!main.getType().translationKey().toUpperCase().contains("SWORD")) return;

        executeParry(p);
    }

    private void executeParry(Player p){
        Location l = p.getLocation();
        ParticleManager pM = new ParticleManager(p.getWorld());
        UUID uuid = p.getUniqueId();

        if(CooldownManager.isOnCooldown(p, "parry")) return;
        CooldownManager.setCooldown(p, "parry", null, 1000);

        pM.spawnParticle(l, Particle.ENCHANTED_HIT,null,
                particleCount,0.5,1.0,0.5,1.0);
        pM.spawnParticle(l, Particle.ELECTRIC_SPARK,null,
                particleCount,0.5,1.0,0.5,1.0);
        p.playSound(l, Sound.BLOCK_NOTE_BLOCK_PLING,0.85F,0.75F);
        p.playSound(l, Sound.ITEM_ARMOR_EQUIP_IRON,0.85F,0.75F);
        p.playSound(l, Sound.BLOCK_ANVIL_PLACE,0.15F,0.75F);

        playerParrys.put(uuid, true);

        new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                tickCount++;

                if(tickCount >= maxParryTicks){
                    if(playerParrys.get(uuid)){
                        pM.spawnParticle(l, Particle.LARGE_SMOKE,null,
                                particleCount,0.5,1.0,0.5,0.0);
                        pM.spawnParticle(l, Particle.RAID_OMEN,null,
                                particleCount,0.5,1.0,0.5,0.0);
                        p.playSound(l, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE,0.15F,0.75F);
                        p.playSound(l, Sound.ENTITY_ELDER_GUARDIAN_CURSE,0.25F,0.5F);
                        playerParrys.put(uuid, false);
                        CooldownManager.setCooldown(p, "parry", null, 5 * 1000);
                    }

                    this.cancel();
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 1);
    }
}
