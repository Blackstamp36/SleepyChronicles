package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class paleSoul extends Zombie {
    private static final trinketItems trinkets = new trinketItems();

    public paleSoul(EntityType<? extends Zombie> type, Level world) {
        super(type, world);

        this.setShouldBurnInDay(false);
        this.setSilent(true);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#cfc4c3") + "Pale Soul"));
        this.addTag("paleSoul");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.325);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
        this.setHealth(10);

        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, null));

        initTimer(this);
    }

    private void initTimer(Zombie z){
        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            if(z.isAlive()) {
                Location l = z.getBukkitEntity().getLocation();
                ParticleBuilder pBuilder = new ParticleBuilder(Particle.SOUL);
                pBuilder.location(z.getBukkitEntity().getLocation())
                        .count(100)
                        .offset(0.25, 0.25, 0.25)
                        .location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ())
                        .spawn();

                z.remove(RemovalReason.KILLED);

                for (Player nearby : z.getBukkitEntity().getLocation().getNearbyPlayers(15)) {
                    nearby.sendActionBar(ChatColor.of("#cfc4c3") + "A nearby pale soul has left..");
                    nearby.playSound(nearby.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1, 1.25F);
                }
            }
        }, 200);
    }

    public static void spawnEntity(Location loc, int entities, Player summoner) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            paleSoul e = new paleSoul(EntityType.ZOMBIE, nmsLvl);
            globalClass global = new globalClass();
            playerData data = global.getPlayerData(summoner.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(summoner);

            if(perksInv.contains(trinkets.createSummonerEmblem())) {
                double currentDamage = e.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
                e.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(currentDamage + (currentDamage * 0.15));
                summoner.sendMessage("Entity spawned with damage modifier!");
            }

            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }
    }

}

