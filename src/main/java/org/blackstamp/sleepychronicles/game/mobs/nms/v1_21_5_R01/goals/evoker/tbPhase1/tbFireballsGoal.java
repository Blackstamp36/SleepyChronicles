package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.believerFireball;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
import org.bukkit.Sound;

import java.util.concurrent.ThreadLocalRandom;

public class tbFireballsGoal extends Goal {

    private final int projectileDamage;
    private final int tickCooldown;

    private final theBeliever entity;

    public tbFireballsGoal(theBeliever entity, int projectileDamage,
                           int tickCooldown) {
        this.entity = entity;
        this.projectileDamage = projectileDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(theBeliever.bossAttacks.FIREBALLS)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.WOLOLO);
    }

    @Override
    public void stop(){
        entity.currentAttack = theBeliever.bossAttacks.LIGHTNING_ORB;
        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.NONE);

        if(entity.getBossPhase() == 2) entity.triggerAtomicDoom(entity.getTarget());
    }

    @Override
    public void tick() {
        super.tick();

        fireProjectiles();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void fireProjectiles() {
        if (!(entity.getTarget() instanceof Player target)) return;
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());

        entity.getLookControl().setLookAt(lookVec);

        if(ThreadLocalRandom.current().nextBoolean()) launchFireballs(90);
        else launchFireballs(45);

        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.85F,1.5F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.85F,1.0F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.85F,0.75F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ITEM_SHIELD_BREAK, 0.45F,1.75F);
    }

    private void launchFireballs(int degrees){
        Level nmsLevel = entity.level();
        final int maxDegrees = (degrees + (90 * 3));
        final Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        for (int i = degrees; i <= maxDegrees; i += 90) {
            believerFireball projectile = new believerFireball(EntityType.ARMOR_STAND, nmsLevel,
                    projectileDamage, 10, entity, i);

            projectile.setPos(startPos);
            nmsLevel.addFreshEntity(projectile);
        }
    }
}
