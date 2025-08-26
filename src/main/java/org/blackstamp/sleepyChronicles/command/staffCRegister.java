package org.blackstamp.sleepyChronicles.command;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.itemRegister;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

public class staffCRegister implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        globalClass global = new globalClass();
        itemRegister iR = new itemRegister();

        if (p.isOp()) {
            if (sender != null && args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "setday":
                        if (args.length > 1) {
                            try {
                                global.setServerDay(Integer.parseInt(args[1]));
                                p.sendMessage(PREFIX + "§7The day was set to §c" + global.getServerDay() + "§7!");
                                p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                            } catch (NumberFormatException e) {
                                p.sendMessage(PREFIX + "§cPlease provide a VALID number to change the day!");
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }

                        } else {
                            p.sendMessage(PREFIX + "§7Enter the day that you want to set!");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;

                    case "items":
                        iR.showItems(p);
                        break;

                    case "broadcast":
                        if (args.length > 1) {
                            for(Player all : Bukkit.getOnlinePlayers()){
                                all.sendMessage(PREFIX + "§7" + args[1]);
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F,1.5F);
                                all.playSound(all.getLocation(), Sound.BLOCK_BELL_USE, 0.85F,1.25F);
                            }
                        } else {
                            p.sendMessage(PREFIX + "§7Command: §e/staff broadcast <message>");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                        break;

                    case "teleport":
                        if (args.length > 1) {
                            if(global.getServerWorlds().containsKey(args[1].toUpperCase())){
                                p.teleport(global.getServerWorlds().get(args[1]));
                            }
                            p.sendMessage(PREFIX + "§7Teleporting to: §e" + args[1]);
                        }
                        break;

                    case "summon":
                        if (args.length > 1) {
                            int summons = 1;

                            if (global.getCustomEntities().containsKey(args[1].toUpperCase())) {
                                try {
                                    if (args.length == 3) summons = Integer.parseInt(args[2]);
                                    Class<?> act = global.getCustomEntities().get(args[1].toUpperCase());
                                    p.sendMessage(PREFIX + "§7Summoned " + summons + "x §c" + args[1] + "§7!");
                                    java.lang.reflect.Method method = act.getMethod("spawnEntity", Location.class, int.class);
                                    method.invoke(null, p.getLocation(), summons);
                                    p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);

                                } catch (NumberFormatException |
                                         NoSuchMethodException |
                                         InvocationTargetException |
                                         IllegalAccessException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                p.sendMessage(PREFIX + "§cNo entity found. Case: §7" + args[1]);
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }
                        } else {
                            p.sendMessage(PREFIX + "§cNo entity provided.");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;

                    case "structures":
                        break;

                    case "settotems":
                        if (args.length > 1) {
                            try {
                                global.setTotems(uuid, Integer.parseInt(args[1]));
                                p.sendMessage(PREFIX + "§7Totems set to §c" + args[1] + "§7!");
                                p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                            } catch (NumberFormatException e) {
                                p.sendMessage(PREFIX + "§cPlease provide a VALID number to change the number of totems consumpt!");
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }

                        } else {
                            p.sendMessage(PREFIX + "§7Enter the totems you want to set!");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;
                }
            }

        } else {
            p.sendMessage(PREFIX + "§cYou don't have the permissions to execute this command!");
            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
        }

        return false;
    }
}


