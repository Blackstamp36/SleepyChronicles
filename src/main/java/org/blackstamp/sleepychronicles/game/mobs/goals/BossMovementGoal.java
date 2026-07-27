package org.blackstamp.sleepychronicles.game.mobs.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossState;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.FlightStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.GroundStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.NavigationStrategy;

import java.util.EnumSet;

public class BossMovementGoal extends Goal {
    private final BossMob boss;
    private final NavigationStrategy navStrategy;
    private final double speed;
    private final double highSpeed;
    private final double slowSpeed;

    private final double retreatRadius;
    private final double maxDistance;
    private final double minDistance;

    private final static int RECALC_TIME = 15;
    private int tickTimer = RECALC_TIME;

    public BossMovementGoal(BossMob boss, double retreatRadius, double minDistance, double maxDistance, double speed){
        this.boss = boss;
        this.retreatRadius = retreatRadius;
        this.speed = speed;
        this.highSpeed = speed * 1.5;
        this.slowSpeed = speed * 0.75;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;

        if(boss.getMovementType().equals(MovementType.FLIGHT)) this.navStrategy = new FlightStrategy();
        else this.navStrategy = new GroundStrategy();

        this.setFlags(EnumSet.of(Flag.MOVE,Flag.LOOK));
    }

    @Override
    public boolean canUse(){
        return boss.getState().equals(BossState.STALKING)
                || boss.getState().equals(BossState.APPROACHING);
    }

    @Override
    public void tick(){
        final LivingEntity target = boss.getTarget();

        if(target == null) return;

        boss.lookAt(target);

        if(this.tickTimer-- > 0) return;

        this.tickTimer = RECALC_TIME;

        float distance = boss.distanceTo(target);

        if(boss.getState() == BossState.STALKING && distance > maxDistance){
            boss.setState(BossState.APPROACHING);
        }

        else if(boss.getState() == BossState.APPROACHING && distance <= maxDistance){
            boss.setState(BossState.STALKING);
        }

        BossState state = boss.getState();

        switch(state){
            case BossState.APPROACHING ->
                    navStrategy.move(boss, target.getX(), target.getY() + (target.getBbHeight()/2), target.getZ(), speed);

            case BossState.STALKING -> {
                if(distance > maxDistance){
                    navStrategy.move(boss, target.getX(), target.getY() + (target.getBbHeight() / 2), target.getZ(), slowSpeed);

                }else if(distance < minDistance){
                    Vec3 retreatPos = navStrategy.calculateRetreatPos(boss,target, retreatRadius);
                    navStrategy.move(boss,retreatPos.x(),retreatPos.y(),retreatPos.z(),highSpeed);

                }else{
                    Vec3 strafePos = navStrategy.calculateStrafePos(boss,target,true);
                    navStrategy.move(boss, strafePos.x(),strafePos.y(),strafePos.z(),slowSpeed);
                }
            }
        }
    }

    @Override
    public void stop(){ boss.setState(BossState.IDLE); }
}