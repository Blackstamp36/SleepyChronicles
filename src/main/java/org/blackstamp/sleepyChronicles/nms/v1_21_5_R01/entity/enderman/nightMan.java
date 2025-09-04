package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.enderman;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class nightMan extends EnderMan {

    public nightMan(EntityType<? extends EnderMan> type, Level world) {
        super(type, world);

        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#cc8033") + "Nightman"));
        this.setAggressive(true);
        this.addTag("nightMan");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.315);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.setHealth(30);

        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            nightMan e = new nightMan(EntityType.ENDERMAN, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);

        }
    }

}
