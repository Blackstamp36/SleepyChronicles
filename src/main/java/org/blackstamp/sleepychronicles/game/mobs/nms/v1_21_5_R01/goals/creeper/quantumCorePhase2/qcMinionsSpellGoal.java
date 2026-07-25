//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2;
//
//import lombok.Getter;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.Vec3;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumCore;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumMinion;
//import org.blackstamp.sleepychronicles.api.particle.ParticleManager;
//import org.bukkit.Location;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.craftbukkit.CraftWorld;
//import org.bukkit.entity.Player;
//
//public class qcMinionsSpellGoal extends Goal {
//
//    private final quantumCore entity;
//    private final int tickCooldown;
//    @Getter
//    private final int minionCount;
//
//    public qcMinionsSpellGoal(quantumCore entity, int minionCount,
//                              int tickCooldown) {
//        this.entity = entity;
//        this.minionCount = minionCount;
//        this.tickCooldown = tickCooldown;
//    }
//
//    @Override
//    public boolean canUse() {
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        return target != null
//                && entity.currentAttack.equals(quantumCore.bossAttacks.MINIONS_SPELL)
//                && entity.getTickCooldown() <= 0;
//    }
//
//    @Override
//    public boolean isInterruptable() {
//        return false;
//    }
//
//    @Override
//    public void start() {
//        entity.increaseTickCooldown(tickCooldown);
//    }
//
//    @Override
//    public void stop() {
//        entity.currentAttack = quantumCore.bossAttacks.QUANTUM_BULLETS;
//    }
//
//    @Override
//    public void tick() {
//        LivingEntity target = entity.getTarget();
//        if (target == null) return;
//
//        executeMinionsSummon();
//    }
//
//    private void executeMinionsSummon(){
//        Location l = entity.getBukkitLivingEntity().getLocation();
//        LivingEntity target = entity.getTarget();
//        if(target == null) return;
//
//        for(int i = 0; i < minionCount; i++)
//            summonMinion(l);
//
//        if(target.getBukkitLivingEntity() instanceof Player p)
//            p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_4,0.85F,1.75F);
//    }
//
//    private void summonMinion(Location l){
//        ParticleManager pM = new ParticleManager(l.getWorld());
//        Vec3 vec3 = new Vec3(l.getX(), l.getY(), l.getZ());
//        Level nmsLevel = ((CraftWorld) l.getWorld()).getHandle();
//        quantumMinion entity = new quantumMinion(EntityType.ZOMBIE, nmsLevel);
//        nmsLevel.addFreshEntity(entity);
//        entity.setPos(vec3);
//        pM.particle(l, Particle.SOUL,null,
//                50,0.25,0.25,0.25, 0.25);
//    }
//
//}
