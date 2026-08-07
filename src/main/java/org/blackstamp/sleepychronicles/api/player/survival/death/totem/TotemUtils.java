package org.blackstamp.sleepychronicles.api.player.survival.death.totem;

import org.blackstamp.sleepychronicles.api.item.templates.BaseItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class TotemUtils {

    public static final PotionEffectType[] totemEffects = {
            PotionEffectType.REGENERATION,
            PotionEffectType.ABSORPTION,
            PotionEffectType.FIRE_RESISTANCE
    };

    public static String getTotemType(ItemStack totem){
        if(totem == null) return null;

        BaseItem manager = new BaseItem(totem);

        return switch(manager.getID()){
            case null, default -> "totem of undying";
        };
    }
}
