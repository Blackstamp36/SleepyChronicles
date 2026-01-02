package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@Registrable
public class onChat implements Listener {
    GlobalClass global = new GlobalClass();

    @EventHandler
    private void onChat(PlayerChatEvent e){
        Player p = e.getPlayer();
        String chatMessage = e.getMessage();
        e.setFormat(p.getScoreboard().getEntityTeam(p).getPrefix() +  p.getName() + " §8» §r" + chatMessage);

        global.sendMessageLog(p, chatMessage);
    }

}
