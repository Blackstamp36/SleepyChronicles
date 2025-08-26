package org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.skeleton;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class banditSkeleton extends Skeleton {

    public banditSkeleton(EntityType<? extends Skeleton> type, Level world) {
        super(type, world);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
        ItemMeta bowMeta = bow.getItemMeta();
        PotionMeta arrowMeta = (PotionMeta) arrow.getItemMeta();
        arrowMeta.setBasePotionType(PotionType.STRONG_HARMING);
        bowMeta.setUnbreakable(true);
        bowMeta.addEnchant(Enchantment.POWER,10,true);
        bow.setItemMeta(bowMeta);
        arrow.setItemMeta(arrowMeta);

        this.setShouldBurnInDay(false);
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#946d51") + "Bandit Skeleton"));
        this.addTag("banditSkeleton");
        this.setItemSlot(EquipmentSlot.MAINHAND, net.minecraft.world.item.ItemStack.fromBukkitCopy(bow));
        this.setItemSlot(EquipmentSlot.OFFHAND, net.minecraft.world.item.ItemStack.fromBukkitCopy(arrow));
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
        this.setHealth(10);

    }

    public static void spawnEntity(Location loc, int entities) {
        ServerLevel nmsLvl = ((CraftWorld) loc.getWorld()).getHandle();

        for (int i = 0; i < entities; i++) {
            banditSkeleton e = new banditSkeleton(EntityType.SKELETON, nmsLvl);
            e.setPos(loc.getX(), loc.getY(), loc.getZ());
            nmsLvl.addFreshEntity(e);
        }

    }

}
