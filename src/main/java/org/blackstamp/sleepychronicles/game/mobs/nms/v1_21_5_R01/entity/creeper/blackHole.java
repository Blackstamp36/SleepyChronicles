package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class blackHole extends Creeper {
    @Setter
    @Getter
    private UUID summonerUUID;
    private int tickCount = 0;

    public blackHole(EntityType<? extends Creeper> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#21292c") + "Blackhole"));
        this.addTag("blackHole");
        this.setHealth(1);
        this.setNoAi(true);
        this.setPowered(true);
        this.setSilent(true);
        this.getBukkitLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,-1,0));
    }

    public static void spawnEntity(Location loc, int entities, Player summoner){
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++){
            blackHole e = new blackHole(EntityType.CREEPER, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            e.setSummonerUUID(summoner.getUniqueId());
            nmsLvl.addFreshEntity(e);

            }
    }

    @Override
    public void tick(){
        super.tick();
        tickCount++;

        if(tickCount >= 100) executeBlackHoleExplosion(this);

        if(tickCount % 20 == 0) attractNearbyMobs(this);
    }

    private void attractNearbyMobs(blackHole entity){
        Location l = entity.getBukkitEntity().getLocation();
        ParticleManager pM = new ParticleManager(l.getWorld());

        for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(6, 3, 6)) {
            if (nearbyMonsters.isInvulnerable()) continue;

            else if(nearbyMonsters instanceof Player p){
                p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE,0.5F,1.25F);
                continue;
            }

            Vector direction = l.toVector().subtract(nearbyMonsters.getLocation().toVector());

            if (direction.length() < 0.001) {
                return;
            }

            direction.normalize().multiply(2.25);

            nearbyMonsters.setVelocity(direction);

            pM.particle(l, Particle.GLOW_SQUID_INK, null,
                    20,0.25,0.25,0.25,1.0);
        }
    }

    private void executeBlackHoleExplosion(blackHole entity){
        Location l = entity.getBukkitEntity().getLocation();
        ParticleManager pM = new ParticleManager(l.getWorld());

        Player summoner = Bukkit.getPlayer(this.getSummonerUUID());

        if (summoner == null) {
            entity.remove(RemovalReason.DISCARDED);
            return;
        }

        for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(6, 3, 6)) {
            if (nearbyMonsters instanceof Player || nearbyMonsters.isInvulnerable()) continue;
            nearbyMonsters.damage(summoner.getHealth() * 2);
        }
        pM.particle(l, Particle.EXPLOSION_EMITTER,null,
                1,0,0,0,1.0);
        pM.particle(l, Particle.SMOKE,null,
                25,0.25,0.25,0.25,1.0);
        entity.remove(RemovalReason.DISCARDED);
    }
}
