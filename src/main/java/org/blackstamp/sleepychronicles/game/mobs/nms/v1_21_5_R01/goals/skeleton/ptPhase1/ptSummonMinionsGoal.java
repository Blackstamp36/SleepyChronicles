package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.skeleton.ptPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.endermite.netherMite;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.banditSkeleton;
import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class ptSummonMinionsGoal extends Goal {

    private final planterrorBoss entity;
    private final int tickCooldown;
    private final int minionCount;

    public ptSummonMinionsGoal(planterrorBoss entity, int minionCount,
                               int tickCooldown) {
        this.entity = entity;
        this.tickCooldown = tickCooldown;
        this.minionCount = minionCount;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(planterrorBoss.bossAttacks.SUMMON_MINIONS)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
    }

    @Override
    public void stop(){
        entity.currentAttack = planterrorBoss.bossAttacks.PINK_SEEDS;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

       executeMinionsSummon();
    }

    private void executeMinionsSummon(){
        Location l = entity.getBukkitLivingEntity().getLocation();
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        for(int i = 0; i < minionCount; i++)
            if(ThreadLocalRandom.current().nextBoolean()) summonNetherMite(l);
            else summonBanditSkeleton(l);

        if(target.getBukkitLivingEntity() instanceof Player p)
            p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0,0.85F,1.75F);
    }

    private void summonNetherMite(Location l){
        ParticleManager pM = new ParticleManager(l.getWorld());
        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        netherMite entity = new netherMite(EntityType.ENDERMITE, nmsLevel);
        nmsLevel.addFreshEntity(entity);
        entity.setPos(vec3);
        pM.particle(l, Particle.SOUL,null,
                25,0.25,0.25,0.25, 0.25);
    }

    private void summonBanditSkeleton(Location l){
        ParticleManager pM = new ParticleManager(l.getWorld());
        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
        banditSkeleton entity = new banditSkeleton(EntityType.SKELETON, nmsLevel);
        nmsLevel.addFreshEntity(entity);
        entity.setPos(vec3);
        pM.particle(l, Particle.SOUL,null,
                25,0.25,0.25,0.25, 0.25);
    }
}
