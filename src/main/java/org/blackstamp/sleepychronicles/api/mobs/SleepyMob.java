package org.blackstamp.sleepychronicles.api.mobs;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.mobs.config.BaseConfig;
import org.blackstamp.sleepychronicles.api.text.SleepyText;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class SleepyMob extends Mob {

    @Getter private BaseConfig config;
    @Setter @Getter private int tickCooldown = 0;
    private CraftMob sleepyBukkitWrapper;

    public SleepyMob(EntityType<? extends Mob> type, Level world, BaseConfig config){
        super(type,world);

        SleepyText displayName = config.displayName();

        this.config = config;
        this.setMobName(displayName.text(), displayName.palette(), displayName.colorType());

        if(config.attributes() != null){
            for(Map.Entry<Holder<Attribute>, Double> entry : config.attributes().entrySet()){

                if(this.getAttributes().hasAttribute(entry.getKey())){
                    this.setAttribute(entry.getKey(), entry.getValue());

                    if(entry.getKey().equals(Attributes.MAX_HEALTH)){ this.setHealth(this.getMaxHealth()); }
                }

            }
        }
    }

    public void setMobName(String name, SleepyPalette palette, int type){
        this.setCustomName(TextFormatter.toComponent(name,palette,type));
    }

    public void setItem(ItemStack item, EquipmentSlot slot){ this.setItemSlot(slot, item); }

    public void setMaxHealth(double max){
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(max);
        this.setHealth(getMaxHealth());
    }

    // Persistent Data related.
    public void setPersistentData(NamespacedKey key, String value){
        PersistentData.set(this.sleepyBukkitWrapper, key, PersistentDataType.STRING, value);
    }
    public String getPersistentData(NamespacedKey key){
        return PersistentData.get(this.sleepyBukkitWrapper, key, PersistentDataType.STRING);
    }

    // Attribute related.
    public void setAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double value){
        AttributeInstance nmsAttribute = this.getAttribute(attribute);

        if(nmsAttribute == null) return;

        nmsAttribute.setBaseValue(value);
    }
    public void amplifyAttribute(@NotNull Holder<Attribute> attribute, @NotNull Double amplifier){
        AttributeInstance nmsAttribute = this.getAttribute(attribute);

        if(nmsAttribute == null) return;

        setAttribute(attribute, nmsAttribute.getBaseValue() * amplifier);
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
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        if(this.config.drops() != null){
            for(ItemStack item : this.config.drops()){
                if(item == null) continue;

                this.spawnAtLocation(level, item);
            }
        }else{ super.dropCustomDeathLoot(level, damageSource, recentlyHit); }
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource source, boolean hitByAPlayer){
        if(this.config.drops() != null && !this.config.drops().isEmpty()){ return; }

        super.dropFromLootTable(level,source,hitByAPlayer);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source){ return config.hurtSound(); }

    @Override
    public SoundEvent getDeathSound(){ return config.deathSound(); }
}