package org.blackstamp.sleepychronicles.game.mobs.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.FlightStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.GroundStrategy;
import org.blackstamp.sleepychronicles.api.mobs.boss.strategies.NavigationStrategy;

import java.util.EnumSet;
import java.util.List;

public class BossDodgeGoal extends Goal {
    private final BossMob boss;
    private final int evadeCooldown;
    private final int evadingTicks;
    private final int radius;
    private final NavigationStrategy navStrategy;
    private final double speed;

    private int currentEvadingTicks;
    private int currentEvadeCooldown;
    private Vec3 evadePos = null;

    public BossDodgeGoal(BossMob boss, int evadeCooldown, int evadingTicks, int radius, double speed){
        this.boss = boss;
        this.evadeCooldown = evadeCooldown;
        this.evadingTicks = evadingTicks;
        this.radius = radius;
        this.speed = speed;

        if(boss.getMovementType().equals(MovementType.FLIGHT)) this.navStrategy = new FlightStrategy();
        else this.navStrategy = new GroundStrategy();

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse(){
        if(currentEvadeCooldown > 0){
            currentEvadeCooldown--;
            return false;
        }

        List<Projectile> projectileList = boss.level().getEntitiesOfClass(
                Projectile.class, boss.getBoundingBox().inflate(radius)
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
    public boolean canContinueToUse(){
        return currentEvadingTicks > 0 && evadePos != null;
    }

    @Override
    public void start(){
        currentEvadeCooldown = evadeCooldown;
        currentEvadingTicks = evadingTicks;

        this.evadePos = navStrategy.calculateStrafePos(
                boss,boss.getTarget(),boss.getRandom().nextBoolean()
        );
    }

    @Override
    public void tick(){
        currentEvadingTicks--;

        if(evadePos != null) boss.getMoveControl().setWantedPosition(evadePos.x,evadePos.y,evadePos.z,speed);
    }
}