package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie;

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
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.globalClass.playerSummons;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

public class stardustGolem extends Zombie {
    private static final trinketItems trinkets = new trinketItems();

    public stardustGolem(EntityType<? extends Zombie> type, Level world) {
        super(type, world);

        this.setShouldBurnInDay(false);
        this.setSilent(true);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#64c7e8") + "Stardust Golem"));
        this.addTag("stardustGolem");
        this.addTag("allyMob");
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.375);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
        this.getAttribute(Attributes.SCALE).setBaseValue(1.25);
        this.setHealth(1);

        this.goalSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, null));

        initTimer(this);
    }

    private void initTimer(Zombie z){
        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            if(z.isAlive()) z.kill((ServerLevel) z.level());
        }, 15 * 20); // 15s Alive.
    }

    public static void spawnEntity(Location loc, int entities, Player summoner) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            stardustGolem e = new stardustGolem(EntityType.ZOMBIE, nmsLvl);
            globalClass global = new globalClass();

            double currentDamage = e.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            e.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(currentDamage * global.getSummonModifier(summoner));

            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }
    }

}

