package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.evoker.tbPhase2;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bat.quantumLight;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.evoker.theBeliever;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class tbQuantumLightsGoal extends Goal {

    private final int tickCooldown;

    private final theBeliever entity;

    public tbQuantumLightsGoal(theBeliever entity, int tickCooldown) {
        this.entity = entity;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        net.minecraft.world.entity.LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(theBeliever.bossAttacks.QUANTUM_LIGHTS)
                && entity.getTickCooldown() <= 0;
    }

    @Override
    public void start() {
        entity.increaseTickCooldown(tickCooldown);
        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.WOLOLO);
    }

    @Override
    public void stop(){
        entity.currentAttack = theBeliever.bossAttacks.FIREBALLS;
        entity.setIsCastingSpell(SpellcasterIllager.IllagerSpell.NONE);

        if(entity.getBossPhase() == 2) entity.triggerAtomicDoom(entity.getTarget());
    }

    @Override
    public void tick() {
        super.tick();

        fireProjectiles();
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), this::fireProjectiles,10);
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void fireProjectiles() {
        if (!(entity.getTarget() instanceof Player target)) return;
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) target.getBukkitLivingEntity();
        Level nmsLevel = entity.level();
        Vec3 lookVec = new Vec3(target.getX(), target.getY(), target.getZ());

        entity.getLookControl().setLookAt(lookVec);
        final Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        quantumLight projectile = new quantumLight(EntityType.BAT, nmsLevel,
                    30, entity, target);

        projectile.setPos(startPos);
        nmsLevel.addFreshEntity(projectile);

        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 0.85F,1.5F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.85F,0.75F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.85F,0.75F);
        bukkitT.playSound(bukkitT.getLocation(), Sound.ITEM_SHIELD_BREAK, 0.45F,1.75F);

    }
}
