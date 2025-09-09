package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@Registrable
public class onChat implements Listener {
    globalClass global = new globalClass();

    @EventHandler
    private void onChat(PlayerChatEvent e){
        Player p = e.getPlayer();
        String chatMessage = e.getMessage();
        e.setFormat(p.getScoreboard().getEntityTeam(p).getPrefix() +  p.getName() + " §8» §r" + chatMessage);

        global.sendMessageLog(p, chatMessage);
    }

}
