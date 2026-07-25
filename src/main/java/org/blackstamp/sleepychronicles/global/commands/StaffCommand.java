package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatUtils;
import org.blackstamp.sleepychronicles.api.constant.ConstantColors;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.inventory.menu.ItemArchive;
import org.blackstamp.sleepychronicles.api.inventory.menu.trinkets.TrinketBag;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.mobs.MobUtils;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
import org.blackstamp.sleepychronicles.game.listener.player.survival.death.totem.TotemManager;
import org.blackstamp.sleepychronicles.game.world.dimensions.WorldUtils;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// todo: add staff-only permission
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
        ChatUtils.sendStaffMessage(p, "Receiving " + amount + "x " + item.name());
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
    @CommandCompletion("@SleepyMobs")
    public void summon(CommandSender sender, @NotNull String mob, @Optional Integer amount){
        if(!(sender instanceof Player p)) return;
        Location l = p.getLocation();
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        if(amount == null || amount < 1) amount = 1;

        for(int i = 0; i < amount; i++){
            SleepyMob entity = MobUtils.instantiate(mob, level);

            if(entity == null) break;
            entity.setPos(l.x(),l.y(),l.z());
            level.addFreshEntity(entity);

            ChatUtils.sendStaffMessage(p, "Summoning " + amount + "x " + mob);
        }
    }

    @Subcommand("teleport")
    public void teleport(CommandSender sender, @NotNull WorldUtils world){
        if(!(sender instanceof Player p)) return;

        p.teleport(world.getLocation());
        ChatUtils.sendStaffMessage(p, "Teleporting to.. " + ConstantColors.YELLOW + world.name());
    }

    @Subcommand("set day ")
    @CommandCompletion("<value>")
    public void setDay(CommandSender sender, @NotNull Integer day){
        if(!(sender instanceof Player p)) return;

        DayManager.getInstance().setDay(day);

        ChatUtils.sendStaffMessage(p, "Day set to.. " + ConstantColors.YELLOW + day);
    }

    @Subcommand("get day")
    public void getDay(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        final int day = DayManager.getInstance().getDay();

        ChatUtils.sendStaffMessage(p, "The current day is " + ConstantColors.YELLOW + day);
    }

    @Subcommand("get time")
    public void getTimeLeft(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        final String timeLeft = DayManager.getInstance().convertToTime(DayManager.getInstance().getTimestamp());

        ChatUtils.sendStaffMessage(p, "Time until next day: " + ConstantColors.YELLOW + timeLeft);
    }

    @Subcommand("get trinkets")
    public void getTrinkets(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        new TrinketBag(p, p.getName()).open();
    }

    @Subcommand("reset")
    public void reset(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        PersistentData.remove(p, SleepyKeys.TRINKETS_INV);
        ChatUtils.sendStaffMessage(p, "Resetting PersistentData...");
    }

    @Subcommand("set totems")
    @CommandCompletion("@PlayersOnline")
    public void setTotems(CommandSender sender, String name, Integer value){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;
        if(value == null) return;

        TotemManager.set(p, value);

        ChatUtils.sendStaffMessage(staff, "The totems of " + ConstantColors.YELLOW + p.getName() + ConstantColors.GREEN + " were set to " + value);
    }

    @Subcommand("get totems")
    @CommandCompletion("@PlayersOnline")
    public void getTotems(CommandSender sender, String name){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;

        Integer totems = TotemManager.get(p);
        String yellow = ConstantColors.YELLOW;
        String green = ConstantColors.GREEN;

        ChatUtils.sendStaffMessage(staff, yellow + p.getName() + green + " has used " + yellow + totems + green + " totems.");
    }
}
