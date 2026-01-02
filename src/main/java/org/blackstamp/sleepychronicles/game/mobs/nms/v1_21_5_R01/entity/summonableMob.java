package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Supplier;

import static org.blackstamp.sleepychronicles.global.GlobalClass.playerMaxSummons;
import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

public interface summonableMob {
    void setSummonerUUID(UUID uuid);
    UUID getSummonerUUID();
    boolean isSummonable();
    Mob getEntity();

    default void applySummonerBonuses(Player summoner) {
        GlobalClass global = new GlobalClass();
        Mob entity = getEntity();

        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            double currentDamage = attackDamage.getBaseValue();
            attackDamage.setBaseValue(currentDamage * global.getSummonDamageModifier(summoner));
        }

        setSummonerUUID(summoner.getUniqueId());
    }

    static void spawnSummonableEntity(Location l, int count, Player summoner,
                                      Supplier<? extends Mob> entitySupplier) {
        GlobalClass global = new GlobalClass();

        UUID uuid = summoner.getUniqueId();

        if(global.hasMaxSummons(summoner)){
            summoner.sendMessage(chatPrefix + "§cYou've reached your max summons! (" + playerMaxSummons.get(uuid) + ")");
            summoner.playSound(summoner.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.85F,0.25F);
            return;
        }

        ServerLevel nmsLevel = ((CraftWorld) l.getWorld()).getHandle();

        for (int i = 0; i < count; i++) {
            Mob entity = entitySupplier.get();

            if (entity instanceof summonableMob ally) {
                ally.applySummonerBonuses(summoner);
            }

            entity.setPos(l.getX(), l.getY(), l.getZ());
            nmsLevel.addFreshEntity(entity);
        }
    }

    default void killSummon(Mob entity) {
        if (entity.isAlive()) entity.kill((ServerLevel) entity.level());
    }
}
