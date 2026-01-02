package org.blackstamp.sleepychronicles.deprecated.commands;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.deprecated.items.itemRegister;
import org.blackstamp.sleepychronicles.deprecated.listener.environment.onWeather;
import org.blackstamp.sleepychronicles.deprecated.loot_table.lootTable;
import org.blackstamp.sleepychronicles.global.utils.clazz.ClassManager;
import org.blackstamp.sleepychronicles.global.utils.manager.ChestManager;
import org.blackstamp.sleepychronicles.global.utils.manager.LootManager;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntityRegistry;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.*;
import java.util.UUID;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

public class DeprecatedStaffCommand implements CommandExecutor {

    private final ClassManager cM = new ClassManager();
    private final NMSEntityRegistry nER;

    public DeprecatedStaffCommand(NMSEntityRegistry nER) {
        this.nER = nER;
    }

    static itemRegister iR = new itemRegister();
    public static Inventory itemsPageOne = iR.getItemsPageOne();
    public static Inventory itemsPageTwo = iR.getItemsPageTwo();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        GlobalClass global = new GlobalClass();

        if (p.isOp()) {
            if (sender != null && args.length > 0) {
                switch (args[0].toLowerCase()){
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
                    case "teleport":
                        if (args.length > 1) {
                            if (global.getServerWorlds().containsKey(args[1].toUpperCase())) {
                                p.teleport(global.getServerWorlds().get(args[1]));
                            }
                            p.sendMessage(chatPrefix + "§7Teleporting to: §e" + args[1]);
                        }
                        break;

                    case "summon":
                        if(args.length < 2) {
                            p.sendMessage(chatPrefix + "§cNo entity provided.");
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                            return true;
                        } else {
                            int summonedEntities = 1;
                            String entityName = args[1].toLowerCase();
                            Class<?> entityClass = nER.getNMSClass(entityName);

                            if (entityClass == null) {
                                p.sendMessage(chatPrefix + "§cDeclared entity not found: " + entityName);
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                return true;
                            }

                            if(args.length == 3){
                                try {
                                    summonedEntities = Integer.parseInt(args[2]);
                                } catch(NumberFormatException e){
                                    p.sendMessage(chatPrefix + "§cValue must be a positive number.");
                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                                    return true;

                                }
                            }

                            try {
                                Location bukkitLoc = p.getLocation();
                                CraftWorld craftWorld = (CraftWorld) bukkitLoc.getWorld();
                                Level nmsWorld = craftWorld.getHandle();

                                Constructor<?> constructor = entityClass.getConstructor(EntityType.class, Level.class);

                                Type genericType = constructor.getGenericParameterTypes()[0];

                                ParameterizedType paramType = (ParameterizedType) genericType;
                                WildcardType wildcard = (WildcardType) paramType.getActualTypeArguments()[0];
                                Class<?> extendedClass = (Class<?>) wildcard.getUpperBounds()[0];

                                EntityType<?> entityType = cM.getEntityTypeForClass(extendedClass);

                                for(int i = 0; i < summonedEntities; i++) {
                                    Entity nmsEntity = (Entity) constructor.newInstance(entityType, nmsWorld);
                                    nmsEntity.setPos(bukkitLoc.getX(), bukkitLoc.getY(), bukkitLoc.getZ());
                                    nmsWorld.addFreshEntity(nmsEntity);
                                }

                                p.sendMessage(chatPrefix + "§7Summoned " + summonedEntities + "x §c" + entityName + "§7!");
                                p.playSound(p.getLocation(), Sound.BLOCK_BONE_BLOCK_BREAK, 0.85F, 0.5F);

                            } catch (Exception e) {
                                p.sendMessage(chatPrefix + "§cFailed to summon entity. Check console for errors.");
                                e.printStackTrace();
                                return true;
                            }
                        }
                        break;

                    case "entities":
                        p.sendMessage(chatPrefix + "§cSending list of the availables entities...");
                        p.playSound(p.getLocation(), Sound.BLOCK_BONE_BLOCK_BREAK, 0.85F, 0.5F);
                        p.sendMessage(nER.getNMSEntitiesMap().keySet().toArray(new String[0]));
                        break;

                    case "chest":
                        if(args.length > 1){
                            LootManager lM = new LootManager();
                            ChestManager cM = new ChestManager();
                            lootTable lootTable = lM.getLootTable(args[1]);

                        if(lootTable == null) return false;

                        cM.placeLootChest(p.getLocation(), lootTable);

                        p.sendMessage(chatPrefix + "§ePlacing loot chest!");
                        p.playSound(p.getLocation(), Sound.BLOCK_BONE_BLOCK_BREAK, 0.85F, 0.5F);
                        }
                        break;

                    case "schematic":
                        if(args.length > 1) {
                            String schematic = args[1];
                            if(global.doesSchemExist(schematic)) {
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



