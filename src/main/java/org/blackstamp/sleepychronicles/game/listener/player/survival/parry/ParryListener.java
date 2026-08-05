package org.blackstamp.sleepychronicles.game.listener.player.survival.parry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.cooldown.CooldownManager;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Registrable
public class ParryListener implements Listener {

    // Color tags.
    private static final String RED_TAG = BasicPalette.RED.tag(true);
    private static final String GREEN_TAG = BasicPalette.GREEN.tag(true);

    // Parry related.
    private static final Set<UUID> PARRY_LIST = new HashSet<>();
    private static final float PARTICLE_OFFSET = 0.85F;
    private static final int PARRY_PERFECT = 2;
    private static final int PARRY_MISS = 5;
    private static final long PARRY_WINDOW = 10L;
    private static final int PARRY_INVINCIBILITY_TICKS = 10;

    @EventHandler
    public void parryInit(PlayerInteractEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        final Material material = e.getMaterial();

        if(!material.name().endsWith("_SWORD")) return;
        if(!e.getAction().isRightClick()) return;
        if(CooldownManager.getInstance().hasCooldown(uuid, ConstantFields.PARRY_KEY)) return;

        new PlayerParryEvent(p,p.getInventory().getItemInMainHand()).callEvent();
    }

    @EventHandler
    public void parryAction(PlayerParryEvent e){
        Player p = e.getPlayer();
        Location l = p.getLocation();
        UUID uuid = p.getUniqueId();
        ParticleManager particleManager = new ParticleManager(p.getWorld());

        particleManager.particle(l, Particle.WAX_ON, null, 25, PARTICLE_OFFSET,1.0);

        PARRY_LIST.add(uuid);
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> parryMiss(p), PARRY_WINDOW);
    }

    @EventHandler
    public void parryPerfect(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        ParticleManager particleManager = new ParticleManager(p.getWorld());
        Location l = p.getLocation();
        UUID uuid = p.getUniqueId();

        if(!PARRY_LIST.contains(uuid)) return;
        if(!ConstantFields.PARRYABLE_CAUSES.contains(e.getCause())) return;

        particleManager.particle(l, Particle.SWEEP_ATTACK, null, 5, PARTICLE_OFFSET,1.0);
        particleManager.sphere(l, Particle.END_ROD, 2,25,1.0, null);

        e.setCancelled(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, PARRY_INVINCIBILITY_TICKS,0,false,false));
        p.playSound(Sound.sound(Key.key("block.anvil.land"), Sound.Source.MASTER, 0.5F, 1.75F));
        ChatManager.sendWarning(p, "✔", GREEN_TAG);
        PARRY_LIST.remove(uuid);

        if(!p.isOnline()) return;
        CooldownManager.getInstance().setCooldown(uuid, ConstantFields.PARRY_KEY, PARRY_PERFECT * ConstantFields.ONE_SECOND);
        parryAvailable(p,PARRY_PERFECT,particleManager);
    }

    private void parryMiss(Player p){
        UUID uuid = p.getUniqueId();
        ParticleManager particleManager = new ParticleManager(p.getWorld());

        if(!PARRY_LIST.contains(uuid)) return;
        if(!p.isOnline()) return;

        ChatManager.sendWarning(p, "❌", RED_TAG);
        p.playSound(Sound.sound(Key.key("entity.zombie.break_wooden_door"), Sound.Source.MASTER, 0.5F, 0.75F));

        PARRY_LIST.remove(uuid);
        if(!p.isOnline()) return;
        CooldownManager.getInstance().setCooldown(uuid, ConstantFields.PARRY_KEY, PARRY_MISS * ConstantFields.ONE_SECOND);
        parryAvailable(p,PARRY_MISS,particleManager);
    }

    private void parryAvailable(Player p, int seconds, ParticleManager particleManager){
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            if(p == null) return;

            p.playSound(Sound.sound(Key.key("ui.stonecutter.take_result"), Sound.Source.MASTER, 0.5F, 1.25F));
            particleManager.sphere(p.getLocation(), Particle.ELECTRIC_SPARK, 2,25,1.0,null);
            }, 20L * seconds);
        }
}