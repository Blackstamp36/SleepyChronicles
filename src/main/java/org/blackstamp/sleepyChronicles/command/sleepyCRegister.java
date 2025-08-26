package org.blackstamp.sleepyChronicles.command;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.*;

public class sleepyCRegister implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        globalClass global = new globalClass();

        if (sender != null && args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "day":
                    p.sendMessage(PREFIX + "§7Currently, we're on day §c" + global.getServerDay() + "§7!");
                    p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                    break;

                case "totem", "totems", "gettotems":
                    int totems = global.getTotems(uuid);
                    p.sendMessage(PREFIX + "§7By the moment, you've consumed §6" + totems + " §7totems!");
                    break;

                case "trinkets":
                    playerData data = global.getPlayerData(uuid);
                    Inventory perksInv = data.getTrinketsAsInventory(p);
                    p.openInventory(perksInv);
                    p.sendMessage(PREFIX + "§7Viewing trinkets..");
                    p.playSound(p, Sound.BLOCK_ENDER_CHEST_OPEN, 1, 0.5F);
                    break;
            }
        }

        return false;
    }
}


