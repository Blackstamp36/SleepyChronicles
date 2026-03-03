package org.blackstamp.sleepychronicles.api.player;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<String> getOnlinePlayers(){
        List<String> online = new ArrayList<>();

        for(Player p : Bukkit.getOnlinePlayers()) online.add(p.getName());
        return online;
    }

    public static void addPots(Player p, PotionEffect[] potions){
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            for(PotionEffect pot : potions) p.addPotionEffect(pot);
        }, 1);
    }

    public static void clearPots(Player p, PotionEffectType[] types){
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            for(PotionEffectType type : types) p.removePotionEffect(type);
        }, 1);
    }
}
