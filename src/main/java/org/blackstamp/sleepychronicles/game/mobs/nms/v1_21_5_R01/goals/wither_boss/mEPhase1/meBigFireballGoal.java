package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.wither_boss.mEPhase1;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.armor_stand.bigFireball;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class meBigFireballGoal extends Goal {

    private final mechanicalEye entity;
    private final int projectileDamage;
    private final int tickCooldown;

    public meBigFireballGoal(mechanicalEye entity, int projectileDamage,
                             int tickCooldown) {
        this.entity = entity;
        this.projectileDamage = projectileDamage;
        this.tickCooldown = tickCooldown;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();

        return target != null
                && entity.currentAttack.equals(mechanicalEye.bossAttacks.BIG_FIREBALL)
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
        entity.currentAttack = mechanicalEye.bossAttacks.GREEN_FLAMES;
    }

    @Override
    public void tick(){
        LivingEntity target = entity.getTarget();
        if(target == null) return;

        shootBigFireball(entity);
    }

    private void shootBigFireball(mechanicalEye entity){
        org.bukkit.entity.Player bukkitT = (org.bukkit.entity.Player) entity.getTarget().getBukkitLivingEntity();
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,0.5F,1.75F);

        Level nmsLevel = entity.level();

        bigFireball projectile = new bigFireball(EntityType.ARMOR_STAND, nmsLevel,
                projectileDamage, 40, entity.getTarget(), entity);

        projectile.setPos(entity.position());
        nmsLevel.addFreshEntity(projectile);

        bukkitT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20,0,false,false));
        bukkitT.playSound(bukkitT.getLocation(), Sound.ENTITY_WITHER_SPAWN,0.5F,0.75F);
    }
}
