package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.llama;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.nms.NMSEntity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@NMSEntity
public class aggresiveLlama extends Llama {

    public aggresiveLlama(EntityType<? extends Llama> type, Level world) {
        super(type, world);
        org.bukkit.entity.Llama llama = (org.bukkit.entity.Llama) this.getBukkitLivingEntity();
        llama.getInventory().setDecor(new ItemStack(Material.GRAY_CARPET));

        this.addTag("aggresiveLlama");
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.375);
        this.setAggressive(true);

        this.targetSelector.getAvailableGoals().clear();

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

}
