package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie;

import com.destroystokyo.paper.ParticleBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.playerSummons;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

public class paleSoul extends Zombie {
    private UUID summonerUUID;
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

    public void setSummoner(UUID summonerUUID) {
        this.summonerUUID = summonerUUID;
    }

    public UUID getSummoner() {
        return summonerUUID;
    }


    private void initTimer(Zombie z){
        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            if(z.isAlive()) z.kill((ServerLevel) z.level());
        }, 200);
    }

    public static void spawnEntity(Location loc, int entities, Player summoner) {
        UUID uuid = summoner.getUniqueId();
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            paleSoul e = new paleSoul(EntityType.ZOMBIE, nmsLvl);
            e.setSummoner(uuid);
            globalClass global = new globalClass();
            playerData data = global.getPlayerData(summoner.getUniqueId());
            Inventory perksInv = data.getTrinketsAsInventory(summoner);
            playerSummons.putIfAbsent(uuid, 0);
            int currentSummons = playerSummons.getOrDefault(uuid,0);

            if(global.hasMaxSummons(summoner)){
                summoner.sendMessage(PREFIX + "§cYou've reached your max summons! (" + playerSummons.get(uuid) + ")");
                summoner.playSound(summoner.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,0.5F,0.5F);
                return;
            }

            playerSummons.put(uuid, currentSummons + 1);
            boolean hasEmblem = perksInv.contains(trinkets.createSummonerEmblem());

            if(hasEmblem) {
                double currentDamage = e.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
                e.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(currentDamage + (currentDamage * 0.15));
            }

            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }
    }

}

