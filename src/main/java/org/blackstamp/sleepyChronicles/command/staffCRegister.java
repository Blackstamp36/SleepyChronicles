package org.blackstamp.sleepyChronicles.command;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.item.itemRegister;
import org.blackstamp.sleepyChronicles.listener.environment.onWeather;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

public class staffCRegister implements CommandExecutor {
    static itemRegister iR = new itemRegister();
    public static Inventory itemsPageOne = iR.getItemsPageOne();
    public static Inventory itemsPageTwo = iR.getItemsPageTwo();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        globalClass global = new globalClass();

        if (p.isOp()) {
            if (sender != null && args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "setday":
                        if (args.length > 1) {
                            try {
                                global.setServerDay(Integer.parseInt(args[1]));
                                p.sendMessage(chatPrefix + "§7The day was set to §c" + global.getServerDay() + "§7!");
                                p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                            } catch (NumberFormatException e) {
                                p.sendMessage(chatPrefix + "§cPlease provide a VALID number to change the day!");
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }

                        } else {
                            p.sendMessage(chatPrefix + "§7Enter the day that you want to set!");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;

                    case "items":
                        p.sendMessage(chatPrefix + "§eOpening items menu!");
                        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1.25F);
                        p.openInventory(itemsPageOne);
                        break;

                    case "broadcast":
                        if (args.length > 1) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(chatPrefix + "§7" + args[1]);
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.5F);
                                all.playSound(all.getLocation(), Sound.BLOCK_BELL_USE, 0.85F, 1.25F);
                            }
                        } else {
                            p.sendMessage(chatPrefix + "§7Command: §e/staff broadcast <message>");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
                        }
                        break;

                    case "teleport":
                        if (args.length > 1) {
                            if (global.getServerWorlds().containsKey(args[1].toUpperCase())) {
                                p.teleport(global.getServerWorlds().get(args[1]));
                            }
                            p.sendMessage(chatPrefix + "§7Teleporting to: §e" + args[1]);
                        }
                        break;

                    case "summon":
                        if (args.length < 2) {
                            p.sendMessage(chatPrefix + "§cNo entity provided.");
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                            return false;
                        }

                        String entityName = args[1].toUpperCase();
                        if (!global.getCustomEntities().containsKey(entityName)) {
                            p.sendMessage(chatPrefix + "§cNo entity found. Case: §7" + entityName);
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                            return false;
                        }

                        int summons = 1;
                        if (args.length == 3) {
                            try {
                                summons = Integer.parseInt(args[2]);
                                if (summons <= 0) {
                                    p.sendMessage(chatPrefix + "§cSummon count must be positive.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                    return false;
                                }
                            } catch (NumberFormatException e) {
                                p.sendMessage(chatPrefix + "§cInvalid number format: §7" + args[2]);
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }
                        }

                        try {
                            Class<?> entityClass = global.getCustomEntities().get(entityName);
                            p.sendMessage(chatPrefix + "§7Summoned " + summons + "x §c" + entityName + "§7!");

                            java.lang.reflect.Method method;
                            try {
                                method = entityClass.getMethod("spawnEntity", Location.class, int.class, Player.class);
                                method.invoke(null, p.getLocation(), summons, p); // THIS LINE IS THE PROBLEM.
                            } catch (NoSuchMethodException e) {
                                method = entityClass.getMethod("spawnEntity", Location.class, int.class);
                                method.invoke(null, p.getLocation(), summons);
                            }

                            p.playSound(p.getLocation(), Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);

                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            p.sendMessage(chatPrefix + "§cFailed to find spawn method for: §7" + entityName);
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            p.sendMessage(chatPrefix + "§cError while summoning entity: §7" + e.getCause().getMessage());
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                            e.printStackTrace();
                        }
                        break;

                    case "schematic":
                        if (args.length > 1) {
                            String schematic = args[1];
                            if (global.doesSchemExist(schematic)) {
                                global.pasteSchematic(p.getLocation(), schematic + ".schem");
                                p.sendMessage(chatPrefix + "§7Placing a §e" + args[1] + "§7!");
                                p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                            } else {
                                p.sendMessage(chatPrefix + "§cDeclared schematic doesn't exist!");
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);

                            }
                        } else {
                            p.sendMessage(chatPrefix + "§cNo schem provided.");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;

                    case "settotems":
                        if (args.length > 1) {
                            try {
                                global.setTotems(uuid, Integer.parseInt(args[1]));
                                p.sendMessage(chatPrefix + "§7Totems set to §c" + args[1] + "§7!");
                                p.playSound(p, Sound.BLOCK_BONE_BLOCK_PLACE, 1, 1);
                            } catch (NumberFormatException e) {
                                p.sendMessage(chatPrefix + "§cPlease provide a VALID number to change the number of totems consumpt!");
                                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return false;
                            }

                        } else {
                            p.sendMessage(chatPrefix + "§7Enter the totems you want to set!");
                            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                        }
                        break;

                    case "storm":
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("start")) {
                                p.getWorld().setStorm(true);
                                p.getWorld().setWeatherDuration(60);

                            } else if (args[1].equalsIgnoreCase("stop")) {
                                onWeather weather = new onWeather();

                                if (onWeather.isStormActive) {
                                    weather.endStormNormally();
                                } else {
                                    p.sendMessage(chatPrefix + "§cThere's no such storm active!");
                                    p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                }

                            }
                        }
                        break;
                }
            }

        } else {
            p.sendMessage(chatPrefix + "§cYou don't have the permissions to execute this command!");
            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
        }

        return false;
    }
}



