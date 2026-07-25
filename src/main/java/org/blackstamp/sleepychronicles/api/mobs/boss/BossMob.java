package org.blackstamp.sleepychronicles.api.mobs.boss;

import co.aikar.commands.annotation.Optional;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAggroGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAttackGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossDodgeGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossMovementGoal;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public abstract class BossMob extends SleepyMob {
    @Setter @Getter private BossAttack queuedAttack = null;
    @Setter @Getter private int tickCooldown = 0;
    @Setter @Getter private int themeCooldown = 0;
    @Getter private int phase = 1;
    @Getter private BossState state;
    @Getter private final MovementType movementType;
    @Getter private final BossAttack[] attacks;
    @Getter private final BossConfig config;
    @Getter private int stateTicks = 0;
    @Getter private final HashMap<UUID,Float> aggroTable = new HashMap<>();

    public BossMob(EntityType<? extends Mob> type, Level world, String name, @Optional String color,
                   MovementType movementType, BossAttack[] attacks, BossConfig config){
        super(type,world,name,color);

        this.movementType = movementType;
        this.attacks = attacks;
        this.config = config;

        setState(BossState.IDLE);
    }

    @Override
    public void tick(){
        super.tick();

        BossConfig config = this.getConfig();

        config.bar().setProgress(this.getHealth() / this.getMaxHealth());

        this.stateTicks++;
        if(this.tickCooldown > 0) tickCooldown--;

        if(!config.bar().getPlayers().isEmpty()) {
            if(this.getThemeCooldown() <= 0){
                playTheme(config.bar().getPlayers());
                this.setThemeCooldown(config.themeTicks());

            }else this.setThemeCooldown(this.getThemeCooldown() - 1);
        }
    }

    private void playTheme(Collection<ServerPlayer> players){
        BossConfig config = this.getConfig();

        for(ServerPlayer p : players){
            ClientboundSoundPacket packet = new ClientboundSoundPacket(
                    Holder.direct(config.soundEvent()),
                    SoundSource.RECORDS,
                    p.getX(),
                    p.getY(),
                    p.getZ(),
                    1.0F,
                    1.0F,
                    0L
            );

            p.connection.send(packet);
        }
    }

    @Override
    public void die(@NonNull DamageSource source){
        super.die(source);

    }

    @Override
    public void startSeenByPlayer(@NonNull ServerPlayer p){
        super.startSeenByPlayer(p);
        this.getConfig().bar().addPlayer(p);
    }

    @Override
    public void stopSeenByPlayer(@NonNull ServerPlayer p){
        super.stopSeenByPlayer(p);

        this.getConfig().bar().removePlayer(p);
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
    public void registerGoals(){
        BossConfig config = this.getConfig();

        this.goalSelector.getAvailableGoals().clear();

        targetSelector.addGoal(0, new BossAggroGoal(
                this,
                config.decay(),
                config.decayTicks()));

        goalSelector.addGoal(0, new BossDodgeGoal(
                this,
                config.evadeCooldown(),
                config.evadingTicks(),
                config.evadeRadius(),
                config.speed()
        ));

        goalSelector.addGoal(1, new BossMovementGoal(
                this,
                config.retreatRadius(),
                config.maxDistance(), this.getConfig().minDistance(),
                config.speed()
                ));

        goalSelector.addGoal(2, new BossAttackGoal(this));
    }

    public void switchToPhase(int value){ phase = value; }
}