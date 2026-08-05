package org.blackstamp.sleepychronicles.api.mobs;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
import org.blackstamp.sleepychronicles.api.mobs.config.BaseConfig;
import org.blackstamp.sleepychronicles.api.text.TextFormatter;
import org.blackstamp.sleepychronicles.game.mobs.goals.sleepy_mobs.GenericSkillGoal;

import java.util.Map;

public interface SleepyEntity {

    BaseConfig getConfig();

    default void applyData(Mob mob){
        BaseConfig config = this.getConfig();
        String color = SleepyPalette.VANILLA.getColor(true);

        if(config.color() != null) color = config.color();
        if(config.attack() != null) mob.goalSelector.addGoal(1, new GenericSkillGoal(mob,config.attack()));

        mob.setCustomName(TextFormatter.toComponent(config.name(),color));

        if(config.attributes() != null){
            for(Map.Entry<Holder<Attribute>, Double> entry : config.attributes().entrySet()){

                if(mob.getAttributes().hasAttribute(entry.getKey())){
                    mob.getAttribute(entry.getKey()).setBaseValue(entry.getValue());
                }
            }

            mob.setHealth(mob.getMaxHealth());
        }
    }

}
