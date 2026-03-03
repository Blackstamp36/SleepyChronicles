package org.blackstamp.sleepychronicles.api.mobs;

import co.aikar.commands.annotation.Optional;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.security.SleepyToken;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

public class SleepyMob {
    @Getter private final LivingEntity entity;
    @Getter private final CraftLivingEntity bukkitEntity;
    @Getter private final Level world;

    @Getter private final String name;

    public SleepyMob(LivingEntity entity, Level world, String name, @Optional String color){
        this.entity = entity;
        this.bukkitEntity = entity.getBukkitLivingEntity();
        this.world = world;
        this.name = entity.getDisplayName().getString();

        if(color == null) color = SleepyPalette.VANILLA.getColor1();
        setName(name, color);
        setID(convertToID(name));
    }

    public void setName(String name, @NotNull String color){
        entity.setCustomName(Component.literal(name).withStyle(Style.EMPTY
                .withColor(TextColor.parseColor(color).getOrThrow())));
    }

    public void setItem(ItemStack item, EquipmentSlot slot){
        entity.setItemSlot(slot, item);
    }

    public void setDamage(double value){ entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(value); }

    public void setMovementSpeed(double value){ entity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(value); }

    public int getDamage(){ return (int) entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue(); }

    public void setHealth(int health){ entity.setHealth(health); }

    public void refillHealth(){ entity.setHealth(getMaxHealth()); }

    public void setMaxHealth(int max){
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(max);
        refillHealth();
    }

    public int getMaxHealth(){ return (int) entity.getAttribute(Attributes.MAX_HEALTH).getBaseValue(); }

    public void setScale(double value){ entity.getAttribute(Attributes.SCALE).setBaseValue(value); }

    public void setFuse(int ticks){
        if(!(bukkitEntity instanceof Creeper c)) return;

        c.setMaxFuseTicks(ticks);
    }

    public void setExplosionPower(int value){
        if(!(bukkitEntity instanceof Creeper c)) return;

        c.setExplosionRadius(value);
    }

    public void setAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double value){
        Objects.requireNonNull(entity.getAttribute(attribute)).setBaseValue(value);
    }

    public AttributeInstance getAttribute(@NotNull Holder<Attribute> attribute){
        return entity.getAttribute(attribute);
    }

    public void amplifyAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double amplifier){
        setAttribute(attribute, getAttribute(attribute).getBaseValue() * amplifier);
    }

    public void setPersistentData(NamespacedKey key, String value){
        PersistentData.set(bukkitEntity, key, PersistentDataType.STRING, value);
    }

    public String getPersistentData(NamespacedKey key){ return PersistentData.get(bukkitEntity, key, PersistentDataType.STRING); }

    public void setID(String value){ setPersistentData(SleepyKeys.MOB_ID, value); }

    public @Nullable String getID(){ return getPersistentData(SleepyKeys.MOB_ID); }

    public String convertToID(String value){ return value.replace(" ","_"); }

    public void setToken(String value){ setPersistentData(SleepyKeys.MOB_TOKEN, value); }

    public @Nullable String getToken(){ return getPersistentData(SleepyKeys.MOB_TOKEN); }

    public void setFamily(String value){ setPersistentData(SleepyKeys.MOB_FAMILY, value); }

    public @Nullable String getFamily(){ return getPersistentData(SleepyKeys.MOB_FAMILY); }

    public void addFreshEntity(Location l){ addFreshEntity(l, CreatureSpawnEvent.SpawnReason.CUSTOM); }

    public void addFreshEntity(Location l, CreatureSpawnEvent.SpawnReason reason){
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        bukkitEntity.teleport(l);
        setToken(SleepyToken.generate());
        level.addFreshEntity(entity, reason);
    }
}