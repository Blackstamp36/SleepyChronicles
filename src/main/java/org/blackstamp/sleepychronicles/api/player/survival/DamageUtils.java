package org.blackstamp.sleepychronicles.api.player.survival;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageUtils {
    private DamageUtils(){}

    // Color tags.
    private static final TextColor DARK_GRAY_TAG = NamedTextColor.DARK_GRAY;

    public static Component getLastDamageCause(Player player, EntityDamageEvent.DamageCause damageCause){
        Component attackerName = getAttackerName(player);

        return switch(damageCause){
            case PROJECTILE -> Component.empty()
                    .append(Component.text("Projectile (").color(DARK_GRAY_TAG))
                    .append(attackerName)
                    .append(Component.text(")").color(DARK_GRAY_TAG));
            case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> Component.empty()
                    .append(Component.text("Explosion (").color(DARK_GRAY_TAG))
                    .append(attackerName)
                    .append(Component.text(")").color(DARK_GRAY_TAG));
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> Component.empty()
                    .append(Component.text("Physical (").color(DARK_GRAY_TAG))
                    .append(attackerName)
                    .append(Component.text(")").color(DARK_GRAY_TAG));
            case SONIC_BOOM -> Component.empty()
                    .append(Component.text("Sonic Boom (").color(DARK_GRAY_TAG))
                    .append(attackerName)
                    .append(Component.text(")").color(DARK_GRAY_TAG));
            case MAGIC, CUSTOM -> Component.empty()
                    .append(Component.text("Magic").color(DARK_GRAY_TAG));
            case FIRE, FIRE_TICK, LAVA, HOT_FLOOR, CAMPFIRE -> Component.empty()
                    .append(Component.text("Melting").color(DARK_GRAY_TAG));
            case KILL, SUICIDE -> Component.empty()
                    .append(Component.text("Suicide").color(DARK_GRAY_TAG));
            case SUFFOCATION, CRAMMING -> Component.empty()
                    .append(Component.text("Suffocation").color(DARK_GRAY_TAG));
            case FALL -> Component.empty()
                    .append(Component.text("Fall").color(DARK_GRAY_TAG));
            case FREEZE -> Component.empty()
                    .append(Component.text("Freeze").color(DARK_GRAY_TAG));
            case CONTACT -> Component.empty()
                    .append(Component.text("Contact").color(DARK_GRAY_TAG));
            case POISON, WITHER -> Component.empty()
                    .append(Component.text("Potion Effect").color(DARK_GRAY_TAG));
            default -> Component.empty()
                    .append(Component.text("Unregistered").color(DARK_GRAY_TAG));
        };
    }

    private static Component getAttackerName(Player player){
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        Component cause = Component.text("?").color(DARK_GRAY_TAG);

        if(lastDamageCause == null) return cause;

        DamageSource damageSource = lastDamageCause.getDamageSource();
        Entity causingEntity = damageSource.getCausingEntity();

        if(causingEntity == null) return cause;

        Component name = causingEntity.customName() != null ? causingEntity.customName() : causingEntity.name();

        return name.color(DARK_GRAY_TAG);
    }
}
