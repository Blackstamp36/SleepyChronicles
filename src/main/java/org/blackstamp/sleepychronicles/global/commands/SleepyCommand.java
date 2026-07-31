package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.inventory.menu.ItemArchive;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("sc|sleepy")
public class SleepyCommand extends BaseCommand { // todo: create party command

        @Subcommand("give")
        public void give(CommandSender sender, @Optional SleepyItems item, @Optional Integer amount){
            if(!(sender instanceof Player p)) return;
            if(item == null){
                ChatManager.sendMessage(p, "Opening items menu!");
                new ItemArchive(p).open();
                return;
            }

            if(amount == null) amount = 1;

            ItemStack stack = item.build().clone();
            stack.setAmount(amount);
            p.getInventory().addItem(stack);
            ChatManager.sendStaffMessage(p, "Receiving " + amount + "x " + item.name());
        }
}
