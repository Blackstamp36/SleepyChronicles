package org.blackstamp.sleepychronicles.api.mobs.boss;

import co.aikar.commands.annotation.Optional;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;

import java.util.HashMap;
import java.util.UUID;

public abstract class BossMob extends SleepyMob {
    @Setter @Getter private BossAttack queuedAttack = null;
    @Setter @Getter private int tickCooldown = 0;
    @Getter private int phase = 1;
    @Getter private BossState state;
    @Getter private final MovementType movementType;
    @Getter private final BossAttack[] attacks;
    @Getter private final String themeKey;
    @Getter private final int themeTicks;
    @Getter private int stateTicks = 0;
    @Getter private final HashMap<UUID,Float> aggroTable = new HashMap<>();

    public BossMob(EntityType<? extends Mob> type, Level world, String name, @Optional String color,
                   MovementType movementType, BossAttack[] attacks, String themeKey, int themeTicks){
        super(type,world,name,color);

        this.movementType = movementType;
        this.attacks = attacks;
        this.themeKey = themeKey;
        this.themeTicks = themeTicks;

        setState(BossState.IDLE);
    }

    public void addAggro(Player p, float value){
        UUID uuid = p.getUUID();
        float damage = aggroTable.getOrDefault(uuid,0F);

        aggroTable.put(uuid,damage + value);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float value){
        boolean hurt = super.hurtServer(level,source,value);
        Entity entity = source.getEntity();

        if(hurt) if(entity instanceof Player p) addAggro(p,value);

        return hurt;
    }

    public void lookAt(LivingEntity target){
        if(target == null) return;

        LookControl look = this.getLookControl();
        look.setLookAt(target);
    }

    public void setState(BossState value){
        this.state = value;
        this.stateTicks = 0;
    }

    @Override
    public void tick(){
        super.tick();

        this.stateTicks++;
        if(this.tickCooldown > 0) tickCooldown--;
    }

    public void switchToPhase(int value){ phase = value; }
}