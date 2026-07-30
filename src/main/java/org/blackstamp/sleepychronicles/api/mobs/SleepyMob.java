package org.blackstamp.sleepychronicles.api.mobs;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.attacks.SleepyAttack;
import org.blackstamp.sleepychronicles.api.mobs.config.BaseConfig;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.goals.sleepy_mobs.GenericSkillGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class SleepyMob extends Mob {
    @Getter private final Level world;
    @Getter private final String mobName;
    @Getter private final String color;
    @Getter private BaseConfig config;

    @Setter @Getter private int tickCooldown = 0;

    private CraftMob sleepyBukkitWrapper;

    @Getter private EntityType<? extends Mob> type;

    public SleepyMob(EntityType<? extends Mob> type, Level world, BaseConfig config){
        super(type,world);

        this.type = type;
        this.world = world;
        this.config = config;
        this.mobName = config.name();

        if(config.color() != null) this.color = config.color();
        else this.color = SleepyPalette.VANILLA.getColor1();

        if(config.attack() != null) this.goalSelector.addGoal(1, new GenericSkillGoal(this,config.attack()));

        this.setMobName(this.mobName, color);

        if(config.attributes() != null){

            for(Map.Entry<Holder<Attribute>, Double> entry : config.attributes().entrySet()){

                if(this.getAttributes().hasAttribute(entry.getKey())){
                    this.setAttribute(entry.getKey(), entry.getValue());

                    if(entry.getKey().equals(Attributes.MAX_HEALTH)){ this.setHealth(this.getMaxHealth()); }
                }

            }
        }
    }

    public void setMobName(String name, @NotNull String color){ this.setCustomName(TextFormatter.toComponent(name,color)); }

    public void setItem(ItemStack item, EquipmentSlot slot){ this.setItemSlot(slot, item); }

    public void setMaxHealth(int max){
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(max);
        this.setHealth(getMaxHealth());
    }

    public void setAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double value){
        this.getAttribute(attribute).setBaseValue(value);
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

    @Override
    public @NonNull CraftEntity getBukkitEntity(){
        if(this.sleepyBukkitWrapper == null) {
            CraftServer server = (CraftServer) Bukkit.getServer();
            this.sleepyBukkitWrapper = new SleepyCraftMob(server, this);
        }

        return this.sleepyBukkitWrapper;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source){ return config.hurtSound(); }

    @Override
    public SoundEvent getDeathSound(){ return config.deathSound(); }
}