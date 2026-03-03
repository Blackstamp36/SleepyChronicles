package org.blackstamp.sleepychronicles.api.mobs.boss;

import co.aikar.commands.annotation.Optional;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;

public abstract class BossMob extends SleepyMob {
    @Getter private final Mob entity;
    @Getter private int phase = 1;
    @Setter @Getter private int tickCooldown = 0;

    public BossMob(Mob entity, Level world, String name, @Optional String color){
        super(entity,world,name,color);

        this.entity = entity;

        if(entity instanceof BossWrapperAware boss) boss.setWrapper(this);

        if(color == null) color = SleepyPalette.VANILLA.getColor1();
        setName(name, color);
        setID(convertToID(name));
    }

    public void switchToPhase(int value){ phase = value; }
    public abstract BossAttacks getNextAttack();
}