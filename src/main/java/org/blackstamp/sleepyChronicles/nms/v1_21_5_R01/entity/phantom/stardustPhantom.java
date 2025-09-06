package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.allyMob;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.goals.ally.copyOwnerTarget;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.playerSummons;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

public class stardustPhantom extends Phantom implements allyMob {
    @Setter
    @Getter
    private UUID summonerUUID;
    int damage = 20;
    int maxHealth = 30;
    float flyingSpeed = 2.75F * 3;
    double mobScale = 1.75D;

    public stardustPhantom(EntityType<? extends Phantom> type, Level world) {
        super(type, world);

        this.addTag("stardustPhantom");
        this.addTag("allyMob");
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#64c7e8") + "Stardust Phantom"));
        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
        this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(flyingSpeed);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        this.setHealth(this.getMaxHealth());
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.setSilent(true);
        this.shouldBurnInDay = false;

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new copyOwnerTarget(this, 1.0, true, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    public boolean isAllyMob(){
        return true;
    }

    public Mob getEntity(){
        return this;
    }

    public static void spawnEntity(Location loc, int entities, org.bukkit.entity.Player summoner) {
        UUID uuid = summoner.getUniqueId();
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            stardustPhantom e = new stardustPhantom(EntityType.PHANTOM, nmsLvl);
            e.setSummonerUUID(uuid);
            globalClass global = new globalClass();
            playerSummons.putIfAbsent(uuid, 0);
            int currentSummons = playerSummons.getOrDefault(uuid,0);

            if(global.hasMaxSummons(summoner)){
                summoner.sendMessage(chatPrefix + "§cYou've reached your max summons! (" + playerSummons.get(uuid) + ")");
                summoner.playSound(summoner.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.5F,0.5F);
                return;
            }

            double currentDamage = e.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            e.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(currentDamage * global.getSummonDamageModifier(summoner));
            playerSummons.put(uuid, currentSummons + 1);

            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }
    }

}


