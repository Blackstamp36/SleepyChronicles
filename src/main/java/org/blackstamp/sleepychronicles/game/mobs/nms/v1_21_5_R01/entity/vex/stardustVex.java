//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.vex;
//
//import lombok.Getter;
//import lombok.Setter;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.monster.Vex;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.ally.copyOwnerTarget;
//import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.craftbukkit.util.CraftChatMessage;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.UUID;
//
//public class stardustVex extends Vex implements summonableMob {
//    @Setter
//    @Getter
//    private UUID summonerUUID;
//    int damage = 40;
//    int maxHealth = 5;
//    float flyingSpeed = 2.75F * 6;
//    double mobScale = 1.5D;
//
//    public stardustVex(EntityType<? extends Vex> type, Level world) {
//        super(type, world);
//
//        this.addTag("stardustVex");
//        this.addTag("allyMob");
//        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#64c7e8") + "Stardust Vex"));
//        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
//        this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(flyingSpeed);
//        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
//        this.setHealth(this.getMaxHealth());
//        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
//        this.setSilent(true);
//
//        this.setItemSlot(EquipmentSlot.MAINHAND, net.minecraft.world.item.ItemStack.fromBukkitCopy(new ItemStack(Material.DIAMOND_AXE)));
//
//        this.targetSelector.getAvailableGoals().clear();
//
//        this.targetSelector.addGoal(1, new copyOwnerTarget(this, 1.0, true, true));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
//    }
//
//    public boolean isSummonable(){
//        return true;
//    }
//
//    public Mob getEntity(){
//        return this;
//    }
//
//}
//
//
