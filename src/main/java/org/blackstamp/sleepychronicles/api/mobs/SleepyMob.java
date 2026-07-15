package org.blackstamp.sleepychronicles.api.mobs;

import co.aikar.commands.annotation.Optional;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.security.SleepyToken;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SleepyMob extends Mob {
    @Getter private final Level world;
    @Getter private final String mobName;
    @Getter private final String mobId;
    @Getter private String mobToken;

    @Getter private EntityType<? extends Mob> type;

    public SleepyMob(EntityType<? extends Mob> type, Level world, String name, @Optional String color){
        super(type,world);

        this.type = type;
        this.world = world;
        this.mobName = name;
        this.mobId = TextFormatter.toIDString(name);
        this.mobToken = SleepyToken.generate();

        if(color == null) color = SleepyPalette.VANILLA.getColor1();
        setMobName(name, color);
    }

    public void setMobName(String name, @NotNull String color){
        this.setCustomName(TextFormatter.toComponent(name,color));
    }

    public void setItem(ItemStack item, EquipmentSlot slot){
        this.setItemSlot(slot, item);
    }

    public void setDamage(double value){ Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(value); }

    public void setMovementSpeed(double value){ Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(value); }

    public int getDamage(){ return (int) Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).getBaseValue(); }

    public void refillHealth(){ this.setHealth(getMaxHealth()); }

    public void setMaxHealth(int max){
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(max);
        this.refillHealth();
    }

    public void setScale(double value){ Objects.requireNonNull(this.getAttribute(Attributes.SCALE)).setBaseValue(value); }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        tag.putString(SleepyKeys.MOB_TOKEN, this.mobToken);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag){
        super.readAdditionalSaveData(tag);

        tag.getString(SleepyKeys.MOB_TOKEN).ifPresent(s -> this.mobToken = s);
    }

    public void setAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double value){
        Objects.requireNonNull(this.getAttribute(attribute)).setBaseValue(value);
    }

    public void amplifyAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double amplifier){
        setAttribute(attribute, Objects.requireNonNull(getAttribute(attribute)).getBaseValue() * amplifier);
    }

    public void addFreshEntity(Location l){ addFreshEntity(l, EntitySpawnReason.MOB_SUMMONED); }

    public void addFreshEntity(Location l, EntitySpawnReason reason){
        LivingEntity entity = type.create(world,reason);

        if(entity == null) return;

        entity.teleportTo(l.x(),l.y(),l.z());
    }
}