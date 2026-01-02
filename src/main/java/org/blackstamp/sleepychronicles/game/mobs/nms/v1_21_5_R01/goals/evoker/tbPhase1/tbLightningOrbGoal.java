package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.lightningOrb;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
import org.bukkit.Sound;

public class tbLightningOrbGoal extends Goal {

    private final int tickCooldown;

    private final theBeliever entity;

    public tbLightningOrbGoal(theBeliever entity, int tickCooldown) {
        this.entity = entity;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(theBeliever.bossAttacks.LIGHTNING_ORB)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.SUMMON_VEX);
    }

    @Override
    public void stop(){
        entity.currentAttack = theBeliever.bossAttacks.ICE_MIST;
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
        Level nmsLevel = entity.level();
        final Vec3 startPos = new Vec3(entity.getX(), entity.getY() + 2.5, entity.getZ());
        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());

        entity.getLookControl().setLookAt(lookVec);

        lightningOrb projectile = new lightningOrb(EntityType.ARMOR_STAND, nmsLevel,
                60, entity);

        projectile.setPos(startPos);
        nmsLevel.addFreshEntity(projectile);

        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.85F,1.5F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.45F,0.5F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.45F,1.5F);
    }
}
