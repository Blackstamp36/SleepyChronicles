//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1;
//
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.monster.SpellcasterIllager;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.Vec3;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.iceMist;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
//import org.bukkit.Sound;
//
//public class tbIceMistGoal extends Goal {
//
//    private final int projectileDamage;
//    private final int tickCooldown;
//
//    private final theBeliever entity;
//
//    public tbIceMistGoal(theBeliever entity, int projectileDamage,
//                         int tickCooldown) {
//        this.entity = entity;
//        this.projectileDamage = projectileDamage;
//        this.tickCooldown = tickCooldown;
//    }
//
//    @Override
//    public boolean canUse() {
//        net.minecraft.world.entity.LivingEntity target = entity.getTarget();
//
//        return target != null
//                && entity.currentAttack.equals(theBeliever.bossAttacks.ICE_MIST)
//                && entity.getTickCooldown() <= 0;
//    }
//
//    @Override
//    public void start() {
//        entity.increaseTickCooldown(tickCooldown);
//        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.WOLOLO);
//    }
//
//    @Override
//    public void stop(){
//        if(entity.getBossPhase() == 1) entity.currentAttack = theBeliever.bossAttacks.FIREBALLS;
//        else entity.currentAttack = theBeliever.bossAttacks.QUANTUM_LIGHTS;
//
//        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.NONE);
//
//        if(entity.getBossPhase() == 2) entity.triggerAtomicDoom(entity.getTarget());
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        fireProjectiles();
//    }
//
//    @Override
//    public boolean isInterruptable() {
//        return false;
//    }
//
//    private void fireProjectiles() {
//        if(!(entity.getTarget() instanceof Player target)) return;
//        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
//        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());
//
//        Level nmsLevel = entity.level();
//        final Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
//
//        entity.getLookControl().setLookAt(lookVec);
//
//        iceMist projectile = new iceMist(EntityType.ARMOR_STAND, nmsLevel,
//                projectileDamage, 30, entity, target);
//
//        projectile.setPos(startPos);
//        nmsLevel.addFreshEntity(projectile);
//
//        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.85F,1.5F);
//        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.85F,0.75F);
//        bukkitT.playSound(bukkitT.getLocation(), Sound.ITEM_BUCKET_FILL_AXOLOTL, 0.45F,1.25F);
//    }
//}
