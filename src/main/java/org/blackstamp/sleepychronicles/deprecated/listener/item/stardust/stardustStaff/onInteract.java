package org.blackstamp.sleepychronicles.deprecated.listener.item.stardust.stardustStaff;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper.stardustCreeper;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.phantom.stardustPhantom;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.vex.stardustVex;
import org.blackstamp.sleepychronicles.global.utils.manager.CooldownManager;
import org.blackstamp.sleepychronicles.global.utils.manager.ParticleManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob.spawnSummonableEntity;

@Registrable
public class onInteract implements Listener {

    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        ParticleManager particleManager = new ParticleManager(l.getWorld());
        PlayerInventory inventory = p.getInventory();
        ItemStack main = inventory.getItemInMainHand();

        if (!e.getAction().toString().contains("RIGHT_CLICK")) return;
        if (!global.isCustomItem(main, "stardust_staff")) return;

        if (!CooldownManager.isOnCooldown(p, "stardust_staff")) {
            spawnRandomStardustMob(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,1, true, false));
            p.playSound(l, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,0.5F,0.5F);
            p.playSound(l, Sound.BLOCK_BREWING_STAND_BREW,0.85F,1.5F);
            particleManager.spawnParticle(l, Particle.ELECTRIC_SPARK,null,
                    15,0.25,0.25,0.25,1.0);
            CooldownManager.setCooldown(p, "stardust_staff", main, 5 * 1000);

        } else CooldownManager.showCooldown(p, "stardust_staff");

    }

    private void spawnRandomStardustMob(Player summoner){
        Location l = summoner.getLocation();
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        int chance = ThreadLocalRandom.current().nextInt(1, 4);

        if(chance == 3) l = l.clone().add(0, 1.5, 0);

        Supplier<Mob> supplier = switch(chance) {
            case 1 -> () -> new stardustPhantom(EntityType.PHANTOM, nmsLevel);
            case 2 -> () -> new stardustCreeper(EntityType.CREEPER, nmsLevel);
            case 3 -> () -> new stardustVex(EntityType.VEX, nmsLevel);
            default -> null;
        };

        if (supplier != null) spawnSummonableEntity(l, 1, summoner, supplier);
    }
}

