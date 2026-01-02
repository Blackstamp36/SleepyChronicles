package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.blackstamp.sleepychronicles.api.chat.ChatUtils;
import org.blackstamp.sleepychronicles.api.inventory.menu.ItemArchive;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.game.world.dimensions.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@CommandAlias("staff")
public class StaffCommand extends BaseCommand {

    @Subcommand("give")
    public void give(CommandSender sender, @Optional SleepyItems item, @Optional Integer amount){
        if(!(sender instanceof Player p)) return;
        if(item == null){
            ChatUtils.sendMessage(p, "Opening items menu!");
            new ItemArchive(p).open();
            return;
        }

        if(amount == null) amount = 1;

        ItemStack stack = item.build().clone();
        stack.setAmount(amount);
        p.getInventory().addItem(stack);
        ChatUtils.sendMessage(p, "Receiving " + amount + "x " + item.name());
    }

    @Subcommand("broadcast")
    @CommandCompletion("<text>")
    public void broadcast(CommandSender sender, String... args){
        if(args.length == 0) return;

        StringBuilder builder = new StringBuilder();
        for(String text : args) builder.append(text).append(" ");

        ChatUtils.sendBroadcast(builder.toString());
    }

    @Subcommand("summon")
    public void summon(CommandSender sender, @NotNull String mob, @Optional Integer amount){
        if(!(sender instanceof Player)) return;
    }

    @Subcommand("teleport")
    public void teleport(CommandSender sender, @NotNull WorldUtils world){
        if(!(sender instanceof Player p)) return;

        p.teleport(world.getLocation());
        ChatUtils.sendMessage(p, "Teleporting to.. <yellow>" + world.name());
    }

    @Subcommand("setday")
    @CommandCompletion("<value>")
    public void setDay(CommandSender sender, @NotNull Integer day){
        if(!(sender instanceof Player p)) return;

        ChatUtils.sendMessage(p, "Day set to.. <yellow>" + day);
    }
}
