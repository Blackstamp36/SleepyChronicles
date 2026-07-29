package org.blackstamp.sleepychronicles.game.mobs.goals.boss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossState;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.FlightStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.GroundStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.NavigationStrategy;

import java.util.EnumSet;
import java.util.List;

public class BossDodgeGoal extends Goal {
    private final BossMob boss;
    private final int dodgeCooldown;
    private final int dodgingTicks;

    private final NavigationStrategy navStrategy;

    private final double dodgeSpeed;

    private final double dodgeDetectionRadius;
    private final double strafeRadius;

    private int currentDodgingTicks;
    private int currentDodgeCooldown;
    private Vec3 evadePos = null;

    public BossDodgeGoal(BossMob boss, int dodgeCooldown, int dodgingTicks, double strafeRadius, double dodgeDetectionRadius, double dodgeSpeed){
        this.boss = boss;
        this.dodgeCooldown = dodgeCooldown;
        this.dodgingTicks = dodgingTicks;
        this.strafeRadius = strafeRadius;
        this.dodgeDetectionRadius = dodgeDetectionRadius;
        this.dodgeSpeed = dodgeSpeed;

        if(boss.getMovementType().equals(MovementType.FLIGHT)) this.navStrategy = new FlightStrategy();
        else this.navStrategy = new GroundStrategy();

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse(){
        if(currentDodgeCooldown > 0){
            currentDodgeCooldown--;
            return false;
        }

        List<Projectile> projectileList = boss.level().getEntitiesOfClass(
                Projectile.class, boss.getBoundingBox().inflate(dodgeDetectionRadius)
        );

        if(projectileList.isEmpty()) return false;

        for(Projectile p : projectileList){
            Vec3 projDir = p.getDeltaMovement().normalize();
            Vec3 bossDir = boss.position().subtract(p.position()).normalize();

            if(projDir.dot(bossDir) > 0.5) return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse(){ return currentDodgingTicks > 0 && evadePos != null; }

    @Override
    public void start(){
        boss.setState(BossState.EVADING);

        currentDodgeCooldown = dodgeCooldown;
        currentDodgingTicks = dodgingTicks;

        LivingEntity entity = boss.getTarget();

        if(entity == null) entity = boss;

        this.evadePos = navStrategy.calculateStrafePos(boss,entity,boss.getRandom().nextBoolean(),strafeRadius);
    }

    @Override
    public void tick(){
        currentDodgingTicks--;

        if(evadePos != null){ navStrategy.move(boss,evadePos.x(), evadePos.y(), evadePos.z(),dodgeSpeed); }
    }

    @Override
    public void stop(){
        evadePos = null;
        if(boss.isAlive()){ boss.setState(BossState.IDLE); }
    }
}