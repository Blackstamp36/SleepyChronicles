package org.blackstamp.sleepychronicles.api.mobs.boss;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.api.mobs.attacks.BossAttack;
import org.blackstamp.sleepychronicles.api.mobs.config.BossConfig;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.BossAggroGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.BossAttackGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.BossDodgeGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.BossMovementGoal;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class BossMob extends SleepyMob {
    @Setter @Getter private BossAttack queuedAttack = null;
    @Setter @Getter private BossAttack previousAttack = null;

    @Setter @Getter private int themeCooldown = 0;

    @Getter private int phase = 1;
    @Getter private BossState state;
    @Getter private final MovementType movementType;
    @Getter private final BossAttack[] bossAttacks;
    @Getter private final BossConfig config;
    @Getter private int stateTicks = 0;
    @Getter private final HashMap<UUID,Float> aggroTable = new HashMap<>();

    public BossMob(EntityType<? extends Mob> type, Level world, BossConfig config){
        super(type,world,config);

        this.movementType = config.movementType();
        this.bossAttacks = config.bossAttacks();
        this.config = config;

        setState(BossState.IDLE);

        if(getMovementType() == MovementType.FLIGHT)
            this.setNoGravity(true);

        this.initGoals();
    }

    @Override
    public void tick(){
        super.tick();

        BossConfig config = this.getConfig();

        config.bar().setProgress(this.getHealth() / this.getMaxHealth());

        this.stateTicks++;

        if(!config.bar().getPlayers().isEmpty()){
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
        look.setLookAt(target, 15F, 15F);

        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        float targetYaw = (float) (Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;

        this.setYRot(targetYaw);
        this.setYBodyRot(targetYaw);
        this.setYHeadRot(targetYaw);
    }

    public boolean isPlayerValid(Player p){
        if(p == null) return false;
        if(!(p instanceof ServerPlayer serverPlayer)) return false;
        if(!serverPlayer.gameMode().equals(GameType.SURVIVAL)) return false;
        if(!serverPlayer.isAlive()) return false;

        ResourceKey<Level> dimension = p.level().dimension();
        ResourceKey<Level> bossDimension = this.level().dimension();

        return dimension == bossDimension;
    }

    public void setState(BossState value){
        if(this.state == value) return;

        this.state = value;
        this.stateTicks = 0;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

    public void initGoals(){
        BossConfig config = this.getConfig();

        this.goalSelector.getAvailableGoals().clear();

        targetSelector.addGoal(0, new BossAggroGoal(
                this,
                config.decay(),
                config.decayTicks(),
                config.aggroRadius(),
                config.maxDistance()
                ));

        goalSelector.addGoal(0, new BossDodgeGoal(
                this,
                config.dodgeCooldown(),
                config.dodgingTicks(),
                config.strafeRadius(),
                config.dodgeDetectionRadius(),
                config.dodgeSpeed()
        ));

        goalSelector.addGoal(1, new BossMovementGoal(
                this,
                config.retreatRadius(),
                config.strafeRadius(),
                config.maxDistance(), this.getConfig().minDistance(),
                config.baseSpeed()
        ));

        goalSelector.addGoal(2, new BossAttackGoal(this));
    }

    @Override
    public boolean shouldDespawnInPeaceful() { return true; }

    @Override
    public boolean isPushable(){ return false; }

    @Override
    public boolean isPickable(){ return true; }

    public void switchToPhase(int value){ phase = value; }
}