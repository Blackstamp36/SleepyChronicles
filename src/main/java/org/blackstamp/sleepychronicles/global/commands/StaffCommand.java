package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.inventory.menu.ItemArchive;
import org.blackstamp.sleepychronicles.api.inventory.menu.trinkets.TrinketBag;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.game.listener.player.survival.death.totem.TotemManager;
import org.blackstamp.sleepychronicles.game.world.dimensions.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

// todo: add staff-only permission
@CommandAlias("staff")
@CommandPermission("admin_privilege")
public class StaffCommand extends BaseCommand {

    @Subcommand("give")
    public void give(CommandSender sender, @Optional SleepyItems item, @Optional Integer amount){
        if(!(sender instanceof Player p)) return;
        if(item == null){
            ChatManager.sendMessage(p, false,"Opening items menu!");
            new ItemArchive(p).open();
            return;
        }

        if(amount == null) amount = 1;

        ItemStack stack = item.build().clone();
        stack.setAmount(amount);
        p.getInventory().addItem(stack);
        ChatManager.sendStaffMessage(p, "Receiving " + amount + "x " + item.name());
    }

    @Subcommand("broadcast")
    @CommandCompletion("<text>")
    public void broadcast(CommandSender sender, String... args){
        if(args.length == 0) return;

        StringBuilder builder = new StringBuilder();
        for(String text : args) builder.append(text).append(" ");

        ChatManager.sendBroadcast(builder.toString());
    }

    @Subcommand("summon")
    @CommandCompletion("@SleepyMobs")
    public void summon(CommandSender sender, @NotNull String mob, @Optional Integer amount){
        if(!(sender instanceof Player p)) return;
        Location l = p.getLocation();
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        if(amount == null || amount < 1) amount = 1;

        for(int i = 0; i < amount; i++){
            Mob entity = MobManager.instantiate(mob, level);

            if(entity == null) break;
            entity.setPos(l.x(),l.y(),l.z());
            level.addFreshEntity(entity);

            ChatManager.sendStaffMessage(p, "Summoning " + amount + "x " + mob);
        }
    }

    @Subcommand("teleport")
    public void teleport(CommandSender sender, @NotNull WorldUtils world){
        if(!(sender instanceof Player p)) return;

        p.teleport(world.getLocation());
        ChatManager.sendStaffMessage(p, "Teleporting to.. " + BasicPalette.YELLOW + world.name());
    }

    @Subcommand("set day ")
    @CommandCompletion("<value>")
    public void setDay(CommandSender sender, @NotNull Integer day){
        if(!(sender instanceof Player p)) return;

        DayManager.getInstance().setDay(day);

        ChatManager.sendStaffMessage(p, "Day set to.. " + BasicPalette.YELLOW + day);
    }

    @Subcommand("get day")
    public void getDay(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        final int day = DayManager.getInstance().getDay();

        ChatManager.sendStaffMessage(p, "The current day is " + BasicPalette.YELLOW + day);
    }

    @Subcommand("get time")
    public void getTimeLeft(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        final String timeLeft = DayManager.getInstance().convertToTime(DayManager.getInstance().getTimestamp());

        ChatManager.sendStaffMessage(p, "Time until next day: " + BasicPalette.YELLOW + timeLeft);
    }

    @Subcommand("get trinkets")
    public void getTrinkets(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        new TrinketBag(p, p.getName()).open();
    }

    @Subcommand("trinkets reset")
    public void resetTrinkets(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        PersistentData.remove(p, SleepyKeys.TRINKETS_INV.get());
        ChatManager.sendStaffMessage(p, "Removing PersistentData...");
    }

    @Subcommand("set totems")
    @CommandCompletion("@PlayersOnline")
    public void setTotems(CommandSender sender, String name, Integer value){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;
        if(value == null) return;

        TotemManager.set(p, value);

        ChatManager.sendStaffMessage(staff, "The totems of " + BasicPalette.YELLOW.getColor() + p.getName() + BasicPalette.GREEN.getColor() + " were set to " + value);
    }

    @Subcommand("get totems")
    @CommandCompletion("@PlayersOnline")
    public void getTotems(CommandSender sender, String name){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;

        Integer totems = TotemManager.get(p);
        String yellow = BasicPalette.YELLOW.getColor();
        String green = BasicPalette.GREEN.getColor();

        ChatManager.sendStaffMessage(staff, yellow + p.getName() + green + " has used " + yellow + totems + green + " totems.");
    }

    @Subcommand("set downed")
    public void down(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRunInstance(uuid);

        if(run == null){
            ChatManager.sendStaffMessage(p,"You're not currently in a run!");
            return;
        }

        RunManager.setDowned(p,run);
    }

    @Subcommand("revive")
    public void revive(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRunInstance(uuid);

        if(run == null){
            ChatManager.sendStaffMessage(p,"You're not currently in a run!");
            return;
        }

        RunManager.revivePlayer(p);
    }
}
