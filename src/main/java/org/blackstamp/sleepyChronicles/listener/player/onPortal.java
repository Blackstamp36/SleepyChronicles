package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.serverDay;

@Registrable
public class onPortal implements Listener {

    @EventHandler
    private void onPortal(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        globalClass global = new globalClass();
        World w = p.getWorld();
        p.sendActionBar("§7You're now on: §e" + global.getWorldTypes().get(w.getName()) + "§7!");
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0.85F);

        if (serverDay <= 14) {
            if (w.getName().equalsIgnoreCase("world_the_end")) {
                double x = p.getLocation().getX();
                double y = p.getLocation().getY() - 65;
                double z = p.getLocation().getZ();
                Location l = new Location(w, x, y, z);

                p.sendTitle("§c...", "§cWhat did you expect to happen?");
                p.teleport(l);
            }
        }
    }
}
