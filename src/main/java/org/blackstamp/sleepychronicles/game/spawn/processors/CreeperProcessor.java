package org.blackstamp.sleepychronicles.game.spawn.processors;

import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.creeper.NullCreeper;
import org.blackstamp.sleepychronicles.game.spawn.MobProcessor;
import org.blackstamp.sleepychronicles.game.spawn.SpawnProcessor;
import org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.creeper.SuppressedCreeper;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

@MobProcessor(EntityType.CREEPER)
public class CreeperProcessor implements SpawnProcessor {

    private static final double SUPPRESSED_CHANCE = 0.25D;

    @Override
    public void process(CreatureSpawnEvent e, int day){
        if(day < 3) return;

        final double chance = ThreadLocalRandom.current().nextDouble();
        Level level = ((CraftWorld) e.getLocation().getWorld()).getHandle();
        final SleepyMob mob;

        if(chance < SUPPRESSED_CHANCE) mob = new SuppressedCreeper(level);
        else mob = new NullCreeper(level);

        mob.addFreshEntity(e.getLocation());
        e.setCancelled(true);
    }
}