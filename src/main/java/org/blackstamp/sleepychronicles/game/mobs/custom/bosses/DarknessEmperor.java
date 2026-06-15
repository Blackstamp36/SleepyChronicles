package org.blackstamp.sleepychronicles.game.mobs.custom.bosses;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.DarknessEmperorAttacks;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAttackGoal;

public class DarknessEmperor extends BossMob { // todo: complete the attributes, goals and else of this class!
    private final static String NAME = "Darkness Emperor";
    private final static String COLOR = "#5e17a1";
    private final static int MAX_HEALTH = 1000;

    public DarknessEmperor(Level level){
        super(EntityType.GHAST, level, NAME, COLOR, MovementType.FLIGHT, DarknessEmperorAttacks.values());
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();

        super.goalSelector.addGoal(0, new BossAttackGoal(this));
    }
}