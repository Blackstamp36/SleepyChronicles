package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.llama;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.ItemStack;

public class aggresiveLlama extends Llama {

    public aggresiveLlama(EntityType<? extends Llama> type, Level world) {
        super(type, world);

        this.addTag("aggresiveLlama");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.375);
        this.setAggressive(true);

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            aggresiveLlama e = new aggresiveLlama(EntityType.LLAMA, nmsLvl);
            org.bukkit.entity.Llama llama = (org.bukkit.entity.Llama) e.getBukkitEntity();
            llama.getInventory().setDecor(new ItemStack(Material.GRAY_CARPET));
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }
    }

}
