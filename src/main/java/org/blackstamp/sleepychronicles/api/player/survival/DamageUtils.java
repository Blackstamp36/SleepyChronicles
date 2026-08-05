package org.blackstamp.sleepychronicles.api.player.survival;

import net.kyori.adventure.text.Component;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageUtils {

    private DamageUtils(){}

    // Color tags.
    private static final String DARK_GRAY_TAG = BasicPalette.DARK_GRAY.tag(true);

    public static String getCauseString(Player p, EntityDamageEvent.DamageCause type){ // todo: check why name == null
        Entity causing = p.getLastDamageCause().getDamageSource().getCausingEntity();
        Component name = Component.text("?");

        if(causing != null) name = causing.customName();

        String show = ConstantFields.MINI_MESSAGE.serialize(name);

        return DARK_GRAY_TAG + switch(type){
            case PROJECTILE -> "Projectile " + "(" + show + DARK_GRAY_TAG + ")";
            case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> "Explosion " + "(" + show + DARK_GRAY_TAG + ")";
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> "Attack " + "(" + show + DARK_GRAY_TAG + ")";
            case SONIC_BOOM -> "Sonic Boom" + "(" + show + DARK_GRAY_TAG + ")";
            case MAGIC, CUSTOM -> "Magic " + "(" + show + DARK_GRAY_TAG + ")";
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
