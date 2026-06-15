package org.blackstamp.sleepychronicles.game.mobs.custom.bosses;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.mobs.MovementType;
import org.blackstamp.sleepychronicles.api.mobs.boss.BossMob;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAggroGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossDodgeGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossMovementGoal;
import org.blackstamp.sleepychronicles.game.mobs.goals.boss.DarknessEmperorAttacks;
import org.blackstamp.sleepychronicles.game.mobs.goals.BossAttackGoal;

public class DarknessEmperor extends BossMob {
    private final static String NAME = "Darkness Emperor";
    private final static String COLOR = "#5e17a1";

    private final static int MAX_HEALTH = 1000;
    private final static float SCALE = 0.55F;
    private final static float KNOCKBACK_RESISTANCE = 1F;

    private final static String THEME_KEY = "";
    private final static int THEME_TICKS = 1000;

    // GOALS
    private final static double SPEED = 0.75D; // todo: complete the record (BossStats), and add it to the parameters of a boss!

    private final static int EVADE_CD = 10;
    private final static int EVADE_TICKS = 10;
    private final static int EVADE_RADIUS = 10;

    private final static int DECAY = 10;
    private final static int DECAY_TICKS = 10;

    private final static int RETREAT_RADIUS = 10;
    private final static int MAX_DISTANCE = 10;
    private final static int MIN_DISTANCE = 10;

    public DarknessEmperor(Level level){
        super(EntityType.GHAST, level, NAME, COLOR,
                MovementType.FLIGHT, DarknessEmperorAttacks.values(),
                THEME_KEY,THEME_TICKS
        );
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.SCALE,SCALE)
                .add(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();

        targetSelector.addGoal(0, new BossAggroGoal(this));

        goalSelector.addGoal(0, new BossDodgeGoal(this)); // Aqui faltaria poner las variables, pero ya comprendes la idea.
        goalSelector.addGoal(1, new BossMovementGoal(this));
        goalSelector.addGoal(2, new BossAttackGoal(this));
    }
}