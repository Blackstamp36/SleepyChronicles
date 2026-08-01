package org.blackstamp.sleepychronicles.api.player.survival;

import net.kyori.adventure.text.Component;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageUtils {

    public static String getCauseString(Player p, EntityDamageEvent.DamageCause type){ // todo: check why name == null
        Entity causing = p.getLastDamageCause().getDamageSource().getCausingEntity();
        Component name = Component.text("?");

        if(causing != null) name = causing.customName();

        String show = ConstantFields.MINI_MESSAGE.serialize(name);

        return BasicPalette.DARK_GRAY.getColor() + switch(type){
            case PROJECTILE -> "Projectile " + "(" + show + BasicPalette.DARK_GRAY.getColor() + ")";
            case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> "Explosion " + "(" + show + BasicPalette.DARK_GRAY.getColor() + ")";
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> "Attack " + "(" + show + BasicPalette.DARK_GRAY.getColor() + ")";
            case SONIC_BOOM -> "Sonic Boom" + "(" + show + BasicPalette.DARK_GRAY.getColor() + ")";
            case MAGIC, CUSTOM -> "Magic " + "(" + show + BasicPalette.DARK_GRAY.getColor() + ")";
            case FIRE, FIRE_TICK, LAVA, HOT_FLOOR, CAMPFIRE -> "Melting";
            case KILL, SUICIDE -> "Suicide";
            case SUFFOCATION, CRAMMING -> "Suffocation";
            case FALL -> "Fall";
            case FREEZE -> "Freeze";
            case CONTACT -> "Contact";
            case POISON -> "Poison";
            case WITHER -> "Wither";
            case DROWNING -> "Drowning";
            case VOID -> "Void";
            case null, default -> "Unknown";
        };
    }
}
