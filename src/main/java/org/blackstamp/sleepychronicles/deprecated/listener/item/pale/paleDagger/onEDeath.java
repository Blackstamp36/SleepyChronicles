package org.blackstamp.sleepychronicles.deprecated.listener.item.pale.paleDagger;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.zombie.paleSoul;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

@Registrable
public class onEDeath implements Listener {
    private final int paleChance = 50; // 50/1000 (5%).
    private final int normalChance = 5; // 5/1000 (0.5%).
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onEDeath(EntityDeathEvent e) {
        Random r = new Random();
        LivingEntity entity = e.getEntity();
        Location l = entity.getLocation();

        // If the biome of the dead entity is a Pale Garden, the killer now has a 5% chance.
        boolean isPaleGarden = (l.getWorld().getBiome(l).toString().contains("PALE")
                && r.nextInt(1,1001) <= paleChance);

        // However, if not. The chance returns to a 0.5% probability.
        boolean isAnyBiome = !l.getWorld().getBiome(l).toString().contains("PALE")
                && r.nextInt(1,1001) <= normalChance;

        // THIS EVENT WILL ONLY EXECUTE IF:
        //- The entity killed was a monster.
        //- The entity died because of a player.
        if(entity.getKiller() != null && entity instanceof Monster) {
            Player p = entity.getKiller();

            // Does the player has the custom item "Pale Dagger" on his mainhand? If not, return.
            if(!global.isCustomItem(p.getInventory().getItemInMainHand(), "pale_dagger")) return;

            // If all successes, it summons the "Pale Soul" entity.
            if(isPaleGarden || isAnyBiome) spawnPaleSoul(p, entity);

                    }
                }

    private void spawnPaleSoul(Player summoner, Entity entity) {
        Location l = entity.getLocation();
        ServerLevel nmsLvl = ((CraftWorld) l.getWorld()).getHandle();
        paleSoul e = new paleSoul(EntityType.ZOMBIE, nmsLvl);
        e.setPos(l.getX(), l.getY(), l.getZ());
        e.setSummonerUUID(summoner.getUniqueId());
        nmsLvl.addFreshEntity(e);

        summoner.playSound(summoner, Sound.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, 1F, 1.5F);
        summoner.playSound(summoner, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 2);
        summoner.playSound(summoner, Sound.BLOCK_BREWING_STAND_BREW, 0.75F, 0);
    }

}

