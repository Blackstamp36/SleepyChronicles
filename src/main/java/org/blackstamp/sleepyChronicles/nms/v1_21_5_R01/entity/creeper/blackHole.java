package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.UUID;

public class blackHole extends Creeper {
    private UUID summonerUUID;
    globalClass global = new globalClass();

    public blackHole(EntityType<? extends Creeper> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#21292c") + "Blackhole"));
        this.addTag("blackHole");
        this.setHealth(1);
        this.setNoAi(true);
        this.setPowered(true);
        this.setSilent(true);
        this.getBukkitLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,-1,0));
        initBlackholeTask(this);
    }

    public void setSummoner(UUID summonerUUID) {
        this.summonerUUID = summonerUUID;
    }

    public UUID getSummoner() {
        return summonerUUID;
    }

    public static void spawnEntity(Location loc, int entities, Player summoner){
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++){
            blackHole e = new blackHole(EntityType.CREEPER, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            e.setSummoner(summoner.getUniqueId());
            nmsLvl.addFreshEntity(e);

            }
    }

    private void initBlackholeTask(Creeper c){
        new BukkitRunnable() {
            @Override
            public void run() {
                Location l = c.getBukkitEntity().getLocation();

                if(!c.isAlive()) this.cancel();

                for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(8, 5, 8)) {
                    if (nearbyMonsters.isInvulnerable()) continue;

                    else if(nearbyMonsters instanceof Player p){
                        p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE,0.5F,1.25F);
                        continue;
                    }

                    Vector direction = l.toVector().subtract(nearbyMonsters.getLocation().toVector());

                    if (direction.length() < 0.001) {
                        return;
                    }

                    direction.normalize();
                    direction.multiply(1.25);

                    nearbyMonsters.setVelocity(direction);
                    global.spawnParticles(l, Particle.GLOW_SQUID_INK, null,10);
                }

            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 20);


        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            Player summoner = Bukkit.getPlayer(this.getSummoner());

            if(c.isAlive() && summoner != null) {
                Location l = c.getBukkitEntity().getLocation();
                ParticleBuilder pBuilder = new ParticleBuilder(Particle.END_ROD);
                pBuilder.location(c.getBukkitEntity().getLocation())
                        .count(75)
                        .offset(0.25, 0.25, 0.25)
                        .location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ())
                        .spawn();

                global.spawnParticles(l, Particle.EXPLOSION_EMITTER,null,1);

                for (LivingEntity nearbyMonsters : l.getNearbyLivingEntities(8, 5, 8)) {
                    if (nearbyMonsters instanceof Player || nearbyMonsters.isInvulnerable()) continue;
                    nearbyMonsters.damage(summoner.getHealth() * 2);
                }

                c.remove(RemovalReason.KILLED);
            }
        }, 100);


    }

}
