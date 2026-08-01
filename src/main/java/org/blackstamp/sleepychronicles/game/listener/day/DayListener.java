package org.blackstamp.sleepychronicles.game.listener.day;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//@Registrable
//public class DayListener implements Listener {
//
//    @EventHandler
//    public void DayChangeEvent(DayChangeEvent e){
//        final int day = e.getDay();
//        final String dayAnnounce = "New day! (Day " + ConstantColors.RED + (day - 1) + ConstantColors.BLUE + " → " + ConstantColors.RED + day + ConstantColors.GRAY + ")";
//
//        ChatManager.sendBroadcast(dayAnnounce);
//
//        Bukkit.getOnlinePlayers().forEach(all -> {
//            all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false, false));
//            all.playSound(Sound.sound(Key.key("entity.zombie.infect"), Sound.Source.MASTER, 1.0F, 1.25F));
//            all.playSound(Sound.sound(Key.key("block.bell.use"), Sound.Source.MASTER, 1.0F, 1.5F));
//        });
//
//    }
//}
