package org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.creeper;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyAttack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuppressedCreeper extends SleepyMob {
    private static final double MOB_SCALE = 1.25D;
    private static final int MAX_HEALTH = 30;
    private static final int EXPLOSION_POWER = 3;

    public SuppressedCreeper(Level level){
        super(new SuppressedCreeperEntity(level), level, "Suppressed","#567f86");

        setFuse(15);
        setScale(MOB_SCALE);
        setExplosionPower(EXPLOSION_POWER);
        setMaxHealth(MAX_HEALTH);
    }

    private static class SuppressedCreeperEntity extends Creeper implements SleepyAttack {
        private final static PotionEffect pot = new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0,false,false);

        public SuppressedCreeperEntity(Level level){ super(EntityType.CREEPER, level); }

        @Override
        public void handleAttack(EntityDamageByEntityEvent e){
            if(!(e.getEntity() instanceof Player p)) return;

            p.addPotionEffect(pot);
        }
    }
}