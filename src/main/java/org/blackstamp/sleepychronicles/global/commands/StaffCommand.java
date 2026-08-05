package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.chat.ChatPrefix;
import org.blackstamp.sleepychronicles.api.constant.SleepyKeys;
import org.blackstamp.sleepychronicles.api.data.PersistentData;
import org.blackstamp.sleepychronicles.api.dungeon.ReviveManager;
import org.blackstamp.sleepychronicles.api.dungeon.RunInstance;
import org.blackstamp.sleepychronicles.api.dungeon.RunManager;
import org.blackstamp.sleepychronicles.api.inventory.menu.ItemArchive;
import org.blackstamp.sleepychronicles.api.inventory.menu.trinkets.TrinketBag;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.mobs.clone.DownedClone;
import org.blackstamp.sleepychronicles.api.mobs.MobManager;
import org.blackstamp.sleepychronicles.game.listener.player.survival.death.totem.TotemManager;
import org.blackstamp.sleepychronicles.api.world.WorldType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

// todo: TEST THE staff-only permission
@CommandAlias("staff")
@CommandPermission("admin_privilege")
public class StaffCommand extends BaseCommand {

    // Colors.

    private static final String YELLOW_TAG = BasicPalette.YELLOW.tag(true);

    @Subcommand("give")
    public void give(CommandSender sender, @Optional SleepyItems item, @Optional Integer amount){
        if(!(sender instanceof Player p)) return;
        if(item == null){
            ChatManager.sendMessage(p, "Opening items menu!", ChatPrefix.STAFF);
            new ItemArchive(p).open();
            return;
        }

        if(amount == null) amount = 1;

        ItemStack stack = item.build().clone();
        stack.setAmount(amount);
        p.getInventory().addItem(stack);
        ChatManager.sendMessage(p, "Receiving " + amount + "x " + item.name(), ChatPrefix.STAFF);
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

        Location location = p.getLocation();
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        if(amount == null || amount < 1) amount = 1;

        for(int i = 0; i < amount; i++){
            Mob entity = MobManager.instantiate(mob, level);

            if(entity == null) break;

            entity.setPos(location.x(), location.y(), location.z());
            level.addFreshEntity(entity);

            ChatManager.sendMessage(p, "Summoning " + amount + "x " + mob, ChatPrefix.STAFF);
        }
    }

    @Subcommand("teleport")
    public void teleport(CommandSender sender, @NotNull WorldType world){
        if(!(sender instanceof Player p)) return;

        p.teleport(world.get().getSpawnLocation());
        ChatManager.sendMessage(p, "Teleporting to.. " + YELLOW_TAG + world.name(), ChatPrefix.STAFF);
    }

    @Subcommand("get trinkets")
    public void getTrinkets(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        new TrinketBag(p, p.getName()).open();
    }

    @Subcommand("set totems")
    @CommandCompletion("@PlayersOnline")
    public void setTotems(CommandSender sender, String name, Integer value){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;
        if(value == null) return;

        TotemManager.set(p, value);

        ChatManager.sendMessage(staff, "The totems of " + p.getName() + " were set to " + value, ChatPrefix.STAFF);
    }

    @Subcommand("get totems")
    @CommandCompletion("@PlayersOnline")
    public void getTotems(CommandSender sender, String name){
        Player p = Bukkit.getPlayer(name);

        if(!(sender instanceof Player staff)) return;
        if(p == null) return;

        Integer totems = TotemManager.get(p);

        ChatManager.sendMessage(staff, p.getName() + " has used " + totems + " totems.", ChatPrefix.STAFF);
    }

    @Subcommand("down")
    public void down(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRunInstance(uuid);

        if(run == null){
            ChatManager.sendMessage(p,"You're not currently in a run!", ChatPrefix.STAFF);
            return;
        }

        ReviveManager.setDowned(p,run);
    }

    @Subcommand("revive")
    public void revive(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        RunInstance run = RunManager.getRunInstance(uuid);

        if(run == null){
            ChatManager.sendMessage(p,"You're not currently in a run!", ChatPrefix.STAFF);
            return;
        }

        ReviveManager.revivePlayer(p,run);
    }

    @Subcommand("reset")
    public void reset(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        p.clearActivePotionEffects();
        PersistentData.remove(p, SleepyKeys.IS_DOWNED.get());
        PersistentData.remove(p, SleepyKeys.TOTEMS.get());
        PersistentData.remove(p, SleepyKeys.TRINKETS_INV.get());

        ChatManager.sendMessage(p,"Resetting all PDCs..");
    }

    @Subcommand("clone")
    public void clone(CommandSender sender){
        if(!(sender instanceof Player p)) return;

        DownedClone clone = new DownedClone(p,p.getLocation());

        clone.showTo(p);
        ChatManager.sendMessage(p,"Summoned fake clone!", ChatPrefix.STAFF);
    }
}
