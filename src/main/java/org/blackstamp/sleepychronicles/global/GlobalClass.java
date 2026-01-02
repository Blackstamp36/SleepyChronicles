package org.blackstamp.sleepychronicles.global;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.game.world.dimensions.AftermathBiomeProvider;
import org.blackstamp.sleepychronicles.game.world.dimensions.AftermathChunkGenerator;
import org.blackstamp.sleepychronicles.deprecated.items.trinket.trinketItems;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.planterrorBoss;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.summonableMob;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creaking.bobCreaking;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper.blackHole;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper.missingId;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.creeper.suppressedCreeper;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.enderman.nightMan;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.enderman.theScreech;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.endermite.netherMite;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.fox.kitsuneFox;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.ghast.eyelessGhast;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast.quantumBeast;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumGolem;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.llama.aggresiveLlama;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.skeleton.banditSkeleton;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.slime.seedGhostSlime;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.spider.voidbornSpider;
import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.zombie.paleSoul;
import org.blackstamp.sleepychronicles.global.utils.adapter.ItemStackTypeAdapter;
import org.blackstamp.sleepychronicles.global.utils.adapter.ListItemStackTypeAdapter;
import org.blackstamp.sleepychronicles.global.utils.data.PlayerData;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static com.mojang.logging.LogUtils.getLogger;

public class GlobalClass {
    @Getter
    public static final Map<UUID, Map<String, Long>> activeCooldowns = new HashMap<>();
    @Getter
    public static JDA discordBot;

    public HashMap<UUID, PickaxeMode> playerPickaxes = new HashMap<>();
    public static HashMap<UUID, Integer> playerSummons = new HashMap<>();
    public static HashMap<UUID, Integer> playerMaxSummons = new HashMap<>();
    public HashMap<UUID, Boolean> pickaxesCooldowns = new HashMap<>();
    public static HashMap<UUID, Boolean> cancelFallDamage = new HashMap<>();
    public static HashMap<UUID, Boolean> playerParrys = new HashMap<>();

    public enum PickaxeMode {
        SILK, FORTUNE
    }

    public void createPlayerData(UUID uuid) {
        File playerFile = getPlayerFile(uuid);
        playerFile.getParentFile().mkdirs();

        if (playerFile.exists()) {
            return;
        }

        PlayerData newData = new PlayerData();

        try (FileWriter writer = new FileWriter(playerFile)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                    .registerTypeAdapter(new TypeToken<List<ItemStack>>() {
                    }.getType(), new ListItemStackTypeAdapter())
                    .create();
            gson.toJson(newData, writer);
            getLogger().info("New data for player: " + uuid);
        } catch (IOException e) {
            getLogger().warn("Couldn't create file for " + uuid + ": " + e.getMessage());
        }
    }

    public void savePlayerData(UUID uuid, PlayerData data) {
        File playerFile = getPlayerFile(uuid);

        try (FileWriter writer = new FileWriter(playerFile)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                    .registerTypeAdapter(new TypeToken<List<ItemStack>>() {
                    }.getType(), new ListItemStackTypeAdapter())
                    .create();

            gson.toJson(data, writer);
        } catch (Exception e) {
            System.out.println("[DEBUG] ERROR DURING SAVE:");
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        File playerFile = getPlayerFile(uuid);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                .registerTypeAdapter(new TypeToken<List<ItemStack>>() {
                }.getType(), new ListItemStackTypeAdapter())
                .create();

        if (!playerFile.exists()) {
            createPlayerData(uuid);
            return new PlayerData();
        }

        try (FileReader reader = new FileReader(playerFile)) {
            return gson.fromJson(reader, PlayerData.class);

        } catch (Exception e) {
            getLogger().warn("Couldn't load data for " + uuid + ": " + e.getMessage());
            return new PlayerData();
        }
    }

    public void setTotems(UUID uuid, int totemsToSet) {
        PlayerData data = getPlayerData(uuid);
        data.setTotems(totemsToSet);
        savePlayerData(uuid, data);
    }

    public void updateTotems(UUID uuid, int totemsToAdd) {
        PlayerData data = getPlayerData(uuid);
        data.setTotems(data.getTotems() + totemsToAdd);
        savePlayerData(uuid, data);
    }

    public int getTotems(UUID uuid) {
        PlayerData data = getPlayerData(uuid);
        return data.getTotems();
    }

    public void updateTrinkets(UUID uuid, Inventory inv) {
        PlayerData data = getPlayerData(uuid);
        data.setTrinketsFromInventory(inv);
        savePlayerData(uuid, data);
    }

    private File getPlayerFile(UUID uuid) {
        return new File("plugins/" + SleepyChronicles.getInstance().getName() + "/" + getPlayersFolder().getName() + "/"+ uuid,
                uuid + ".json");
    }

    public File getPlayersFolder() {
        return new File("plugins/" + SleepyChronicles.getInstance().getName(), "players");
    }

    public void createMainFiles() {
        // This file stores the current day integer value.
        // This will only be created once. And in a case that the file is lost, it tries to create it again here.

        File mainFile = getMainFile();
        getPluginDir();
        getSchematicsFolder();
        getPlayersFolder();

        if (mainFile.exists()) return;

        try (FileWriter writer = new FileWriter(mainFile)) {
            Map<String, Integer> daysMap = new HashMap<>();
            daysMap.put("day", 1);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            gson.toJson(daysMap, writer);

            getLogger().info("Main file created successfully!");
        } catch (IOException e) {
            getLogger().warn("Couldn't create Main file: " + e.getMessage());
        }
    }

    public File getPluginDir(){
        File dir = new File("plugins", "SleepyChronicles");
        if(!dir.exists()) dir.mkdir();

        return dir;
    }

    private File getMainFile() {
        return new File("plugins/" + SleepyChronicles.getInstance().getName(), "mainFile.json");
    }

    public File getSchematicsFolder(){
        File schematicFolder = new File("plugins/" + SleepyChronicles.getInstance().getName(), "schematics");
        if(!schematicFolder.exists()) schematicFolder.mkdir();

        return schematicFolder;
    }

    public int getServerDay() {
        Gson gson = new Gson();
        File mainFile = getMainFile();

        if (!mainFile.exists()) {
            System.out.println("File " + mainFile.getName() + " not found!");
            return 1;
        }

        try (FileReader fileR = new FileReader(mainFile)) {
            Type type = new TypeToken<Map<String, Integer>>() {
            }.getType();
            Map<String, Integer> data = gson.fromJson(fileR, type);

            if (data.get("days") == null) return 1;

            return data.get("days");

        } catch (Exception e) {
            System.out.println("An exception has ocurred in getServerDay! " + e.getMessage());
        }

        return 1;
    }

    public void setServerDay(int day) {
        File file = getMainFile();
        Map<String, Integer> daysMap = new HashMap<>();
        daysMap.put("days", day);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fileW = new FileWriter(file)) {
            gson.toJson(daysMap, fileW);

        } catch (IOException e) {
            System.out.println("An exception has ocurred in setServerDay! " + e.getMessage());
        }
    }

    public void spawnParticles(Location l, @NotNull Particle particle, @Nullable Material material, @Nullable Integer count) {
        ParticleBuilder pBuilder = new ParticleBuilder(particle);

        /**
         Spawns in the server a group of particles, so everyone can see it.
         **/

        pBuilder.location(l)
                .count(25)
                .offset(0.05, 0.05, 0.05)
                .location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ());

        if (material != null) {
            pBuilder.data(material.createBlockData());
        }

        if (count != null) {
            pBuilder.count(count);
        }

        pBuilder.spawn();
    }

    public void teleportRandom(Entity e, double radius) {
        Location l = e.getLocation();
        Location newL = new Location(l.getWorld(), l.getX() + radius, l.getY(), l.getZ() + radius);

        e.teleport(newL);
    }

    public Map<String, Class> getCustomEntities() {
        final Map<String, Class> entityRegistry = new HashMap<>();
        entityRegistry.put("BOB", bobCreaking.class);
        entityRegistry.put("MISSINGID", missingId.class);
        entityRegistry.put("SUPPRESSED", suppressedCreeper.class);
        entityRegistry.put("LLAMA", aggresiveLlama.class);
        entityRegistry.put("KITSUNE", kitsuneFox.class);
        entityRegistry.put("NIGHTMAN", nightMan.class);
        entityRegistry.put("NETHERMITE", netherMite.class);
        entityRegistry.put("EYELESSGHAST", eyelessGhast.class);
        entityRegistry.put("QUANTUMGOLEM", quantumGolem.class);
        entityRegistry.put("SEEKER", seekerPhantom.class);
        entityRegistry.put("BANDITSKELETON", banditSkeleton.class);
        entityRegistry.put("SEEDGHOST", seedGhostSlime.class);
        entityRegistry.put("VOIDBORNSPIDER", voidbornSpider.class);
        entityRegistry.put("MECHANICALEYE", mechanicalEye.class);
        entityRegistry.put("BREEZERA", planterrorBoss.class);
        entityRegistry.put("PALESOUL", paleSoul.class);
        entityRegistry.put("SCREECH", theScreech.class);
        entityRegistry.put("BLACKHOLE", blackHole.class);
        entityRegistry.put("QUANTUMBEAST", quantumBeast.class);

        return entityRegistry;
    }

    public Map<String, Location> getServerWorlds() {
        final Map<String, Location> entityRegistry = new HashMap<>();
        entityRegistry.put("OVERWORLD", Bukkit.getWorld("world").getSpawnLocation());
        entityRegistry.put("NETHER", Bukkit.getWorld("world_nether").getSpawnLocation());
        entityRegistry.put("END", Bukkit.getWorld("world_the_end").getSpawnLocation());
        entityRegistry.put("AFTERMATH", Bukkit.getWorld("world_aftermath").getSpawnLocation());

        return entityRegistry;
    }

    public HashMap<String, String> getWorldTypes() {
        HashMap<String, String> worldTypes = new HashMap<>();
        worldTypes.put("world", "The Overworld");
        worldTypes.put("world_nether", "The Nether");
        worldTypes.put("world_the_end", "The End");
        worldTypes.put("world_aftermath", "The Aftermath");

        return worldTypes;
    }

    public void initPlayerTasks() {
        final Map<UUID, Boolean> hasNullTNT = new HashMap<>();
        final Map<UUID, Boolean> hasBobSoul = new HashMap<>();
        final Map<UUID, Boolean> hasQuantumCore = new HashMap<>();
        final Map<UUID, Boolean> hasQuantumReactor = new HashMap<>();
        final Map<UUID, Boolean> hasBobMiracle = new HashMap<>();

        trinketItems trinkets = new trinketItems();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    checkImperceptibility(all);

                    int maxSummons = 2;
                    UUID uuid = all.getUniqueId();
                    GlobalClass global = new GlobalClass();

                    PlayerData data = global.getPlayerData(uuid);
                    Inventory perksInv = data.getTrinketsAsInventory(all);

                    boolean currentlyHasNullTNT = perksInv.contains(trinkets.createNullTNT());
                    boolean currentlyHasBobSoul = perksInv.contains(trinkets.createBobSoul());
                    boolean currentlyHasQuantumCore = perksInv.contains(trinkets.createQuantumCore());
                    boolean currentlyHasQuantumReactor = perksInv.contains(trinkets.createQuantumReactor());
                    boolean currentlyHasBobMiracle = perksInv.contains(trinkets.createBobMiracle());

                    hasNullTNT.put(uuid, currentlyHasNullTNT);
                    hasBobSoul.put(uuid, currentlyHasBobSoul);
                    hasQuantumCore.put(uuid, currentlyHasQuantumCore);
                    hasQuantumReactor.put(uuid, currentlyHasQuantumReactor);
                    hasBobMiracle.put(uuid, currentlyHasBobMiracle);

                    double baseHealth = 20.0;
                    double baseSpeed = 0.1;
                    double healthModification = 0.0;
                    double speedModification = 1.0;

                    if (hasNullTNT.get(uuid)) {
                        healthModification -= 2.0;
                    }

                    if (hasBobSoul.get(uuid)) {
                        healthModification += 4.0;
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1, true, false));
                        all.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 0, true, false));
                    }

                    if (hasQuantumCore.get(uuid)) {
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1, true, false));
                    }

                    if (hasQuantumReactor.get(uuid)) {
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, true, false));
                    }

                    if (hasBobMiracle.get(uuid)) healthModification += 8.0;

                    if(hasCustomArmor(all, "solar")) healthModification += 12.0;

                    if(hasCustomArmor(all, "vortex")) {
                        healthModification += 8.0;
                        speedModification += 0.25;
                    }

                    if(hasCustomArmor(all, "stardust")){
                        healthModification += 8.0;
                        maxSummons += 4;
                    }

                    playerMaxSummons.put(uuid, maxSummons);
                    all.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(baseSpeed * speedModification);
                    all.setMaxHealth(baseHealth + healthModification);
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 60);
    }

    public boolean isNextItem(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.LIME_DYE) return false;

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            return meta.hasDisplayName() && meta.getDisplayName().equals("§aNext");
        }
        return false;
    }

    public boolean isBackItem(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.RED_DYE) return false;

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            return meta.hasDisplayName() && meta.getDisplayName().equals("§cBack");
        }
        return false;
    }

    private void checkImperceptibility(Player p) {
        if (p.hasPotionEffect(PotionEffectType.WEAVING)) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(p);
            }
        } else {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(p);
            }
        }

    }

    public void removeTotemInitialEffects(Player p) {
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }, 1);
    }

    public void initDiscordBot() {
        if (discordBot == null) {
            try {
                discordBot = JDABuilder.createDefault("MTQwMDkzNzI4NjY2NDg1MTY0MQ.GropTH.WlPKLg5EHI_U7whHMxuBltsab8U2mlpog9oAMc")
                        .setActivity(Activity.playing("Viewing deaths.."))
                        .build();
                discordBot.awaitReady();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageLog(Player p, String message) {
        Guild guild = discordBot.getGuildById(1393327785606512753L);

        TextChannel channel;
        if (guild != null) {
            channel = guild.getTextChannelById(1411217744606789724L);
        } else {
            System.out.println("No guild found! Returning.. ");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();

        embed.addField(p.getName(), message, false);
        channel.sendMessageEmbeds(embed.build()).queue();

    }

    public void showDiscordDeath(Player p, Location dL, String finalCause, String dimension, String deathMessage) {
        Guild guild = discordBot.getGuildById(1393327785606512753L);

        TextChannel channel;
        if (guild != null) {
            channel = guild.getTextChannelById(1400936730550599873L);
        } else {
            System.out.println("No guild found! Returning.. =(");
            return;
        }

        String causeWithoutColorCodes = finalCause.replaceAll("§[0-9a-zA-Z]", "");

        EmbedBuilder embed = new EmbedBuilder();

        int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        embed.setColor(new java.awt.Color(213, 24, 24));
        embed.setTitle(p.getName());
        embed.addField("Death reason: 💀", causeWithoutColorCodes, true);
        embed.addField("Coordinates: 🧭",
                "X: " + (int) dL.getX() + ", Y: " + (int) dL.getY() + ", Z: " + (int) dL.getZ() + " (" + dimension + ")", true);
        embed.addField("Time: 🕛", "<t:" + currentTime + ">", true);
        embed.addField("Death message: :eye_in_speech_bubble: ", deathMessage, true);
        embed.setThumbnail("http://cravatar.eu/helmavatar/" + p.getName() + "/128");
        channel.sendMessageEmbeds(embed.build()).queue();

    }

    public void showTotemUse(Player p, int totems, String totemName, String finalCause) {
        Guild guild = discordBot.getGuildById(1393327785606512753L);

        TextChannel channel;
        if (guild != null) {
            channel = guild.getTextChannelById(1400936807192854653L);

        } else {
            System.out.println("No guild found! Returning.. ");
            return;
        }

        String causeWithoutColorCodes = finalCause.replaceAll("§[0-9a-zA-Z]", "");

        EmbedBuilder embed = new EmbedBuilder();

        int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        embed.setColor(new java.awt.Color(245, 199, 14));
        embed.setTitle(p.getName() + " • N°" + totems);
        embed.addField("Caused by: 💀", causeWithoutColorCodes, true);
        embed.addField("Time: 🕛", "<t:" + currentTime + ">", true);
        switch(totemName) {
            case "Wooden Totem":
                embed.setThumbnail("https://cdn.discordapp.com/attachments/1411147147910582413/1411147410134143037/big_wooden_totem.png?ex=68b398f0&is=68b24770&hm=9e4ab4de6f22c02eeff0d736d0e20a33c031610c6104c36002c5e1093af60973&");
                break;

            default:
                embed.setThumbnail("https://cdn.discordapp.com/attachments/1411147147910582413/1411147158199205928/image.png?ex=68b398b4&is=68b24734&hm=165c444ab637ff7bb95f053d0b23698f566b0b1a201e0fbd42172c0deafada98&");
                break;
        }
        channel.sendMessageEmbeds(embed.build()).queue();

    }

    public File getSchematicFolder() {
        return new File(SleepyChronicles.getInstance().getDataFolder(), "schematics");
    }

    public boolean pasteSchematic(Location bukkitLocation, String schematicFileName) {
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(bukkitLocation.getWorld());
        BlockVector3 wePosition = BukkitAdapter.asBlockVector(bukkitLocation);

        File schemFile = new File(getSchematicFolder(), schematicFileName);

        if (!schemFile.exists()) {
            SleepyChronicles.getInstance().getLogger().warning("Schematic file not found: " + schemFile.getAbsolutePath());
            return false;
        }

        ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
        if (format == null) {
            SleepyChronicles.getInstance().getLogger().warning("Unknown schematic format for file: " + schematicFileName);
            return false;
        }

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld);
             FileInputStream fileInputStream = new FileInputStream(schemFile);
             ClipboardReader reader = format.getReader(fileInputStream)) {

            Clipboard clipboard = reader.read();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(wePosition)
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
            editSession.close();

            return true;

        } catch (IOException e) {
            SleepyChronicles.getInstance().getLogger().severe("Failed to paste schematic: " + e.getMessage());

        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean doesSchemExist(String schematic){
        File schemFile = new File(getSchematicFolder(), schematic + ".schem");

        return schemFile.exists();
    }

    public void createTomb(Player p) {
        String[] availableTombstones = {
                "tombstone_spruce",
                "tombstone_ice",
                "tombstone_jungle"
        };
        Random r = new Random();
        Location pL = p.getLocation();

        pasteSchematic(pL, availableTombstones[r.nextInt(availableTombstones.length)] + ".schem");

        Location headLoc = new Location(p.getWorld(), pL.getX(), pL.getY(), pL.getZ());

        Material head = Material.PLAYER_HEAD;

        headLoc.getBlock().setType(head);

        Block headBlock = headLoc.getBlock();

        if (headBlock.getState() instanceof Skull skullState) {
            skullState.setOwner(p.getName());
            skullState.setRotation(p.getFacing());
            skullState.update();

        }

    }

    public boolean hasCustomArmor(Player p, String armor) {
        Inventory inventory = p.getInventory();
        ItemStack[] armorPieces = {
                inventory.getItem(39), // helmet
                inventory.getItem(38), // chestplate
                inventory.getItem(37), // leggings
                inventory.getItem(36)  // boots
        };

        for (ItemStack piece : armorPieces) {
            if (piece == null || !piece.hasItemMeta()) {
                return false;

            }

            ItemMeta meta = piece.getItemMeta();
            if (!meta.hasCustomModelDataComponent()) {
                return false;
            }

            CustomModelDataComponent data = meta.getCustomModelDataComponent();
            if (!data.getStrings().contains(armor)) {
                return false;
            }
        }

        return true;

    }

    public boolean hasMaxSummons(Player p){
        UUID uuid = p.getUniqueId();
        playerSummons.putIfAbsent(uuid, 0);

        return playerSummons.get(uuid) >= playerMaxSummons.get(uuid);
    }

    public double getSummonDamageModifier(Player p){
        double damage = 1.0;
        trinketItems trinkets = new trinketItems();
        PlayerData data = getPlayerData(p.getUniqueId());
        Inventory perksInv = data.getTrinketsAsInventory(p);

        boolean hasEmblem = perksInv.contains(trinkets.createSummonerEmblem());

        if(hasEmblem) damage += 0.15;
        if(hasCustomArmor(p, "stardust")) damage += 0.3;

        return damage;
    }

    public void modifyBossHealth(net.minecraft.world.entity.LivingEntity boss){
        LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        Collection<org.bukkit.entity.Player> playersNearby = bukkitBoss.getLocation().getNearbyPlayers(35);
        Collection<org.bukkit.entity.Player> playersInSurvival = new ArrayList<>();

        for(org.bukkit.entity.Player p : playersNearby){
            if(p.getGameMode().equals(GameMode.SURVIVAL)) playersInSurvival.add(p);
        }

        int actualPlayerCount = playersInSurvival.size();

        boss.getAttribute(Attributes.MAX_HEALTH).setBaseValue(boss.getMaxHealth() * actualPlayerCount);
        boss.setHealth(boss.getMaxHealth());
    }

    public void modifyAllyHealth(summonableMob ally){
        net.minecraft.world.entity.LivingEntity entity = ally.getEntity();
        Player summoner = Bukkit.getPlayer(ally.getSummonerUUID());

        if(summoner == null) return;

        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue((int) (entity.getMaxHealth() + summoner.getHealth()));
        entity.setHealth(entity.getMaxHealth());
    }

    public boolean isCustomItem(ItemStack main, String cmdComponent) {
        if (main.hasItemMeta()) {
            ItemMeta mainMeta = main.getItemMeta();

            if (mainMeta.hasCustomModelDataComponent()) {
                CustomModelDataComponent data = mainMeta.getCustomModelDataComponent();

                return data.getStrings().contains(cmdComponent);
            }
        }

        return false;
    }

    public void initChangeDayTask() {
        Bukkit.getScheduler().runTaskTimer(SleepyChronicles.getInstance(), () -> {
            setServerDay(getServerDay() + 1);
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false, false));
                all.sendTitle("§7§k|§r §5Day passed! §7§k|", "§7Day §c" + (getServerDay() - 1) + " §6→→ " + "§7Day §c" + getServerDay() + "§7!");
                all.playSound(all, Sound.ENTITY_RAVAGER_DEATH, 1, 0.5F);
            });

        }, 1728000, 1728000); //1728000 ticks = 1 IRL day
    }

    public void registerPlayerTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        record TeamConfig(String name, String prefix, ChatColor color) {
        }

        TeamConfig[] teams = {
                new TeamConfig("admin", "§f\uE000 §8| §e", ChatColor.YELLOW),
                new TeamConfig("staff", "§f\uE001 §8| §9", ChatColor.BLUE),
                new TeamConfig("player", "§f\uE002 §8| §2", ChatColor.DARK_GREEN),
                new TeamConfig("dead", "§f\uE003 §8| §7", ChatColor.GRAY)
        };

        for (TeamConfig config : teams) {
            if (scoreboard.getTeam(config.name) == null) {
                System.out.println("No " + config.name + " team detected! Creating new one..");
                Team team = scoreboard.registerNewTeam(config.name);
                team.addEntry(config.name);
                team.setPrefix(config.prefix);
                team.setColor(config.color);

            }

        }

    }

    public void createAftermathDimension(){
        WorldCreator worldCreator = WorldCreator.name("world_aftermath")
                .environment(World.Environment.NORMAL)
                .type(WorldType.NORMAL)
                .biomeProvider(new AftermathBiomeProvider())
                .generator(new AftermathChunkGenerator());

        World world = worldCreator.createWorld();
        if (world != null) {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setTime(13000);
            world.setStorm(false);
            world.setThundering(false);

            WorldBorder border = world.getWorldBorder();
            border.setCenter(0.0, 0.0);
            border.setSize(10000.0);
        }
    }

    public void initBossBarTask(net.minecraft.world.entity.LivingEntity boss, String bossName, BarColor barColor, String hexColor){
        final BossBar finalBossBar = createBossbar(bossName, barColor, hexColor);
        LivingEntity bukkitBoss = boss.getBukkitLivingEntity();
        if(!(boss instanceof bossMob bossMob)) return;
        String halfHealthTitle =  bossName.concat(" §c[\uD83D\uDD25]");

        new BukkitRunnable() {
            @Override
            public void run(){

                if (bukkitBoss.isDead() || !bukkitBoss.isValid()) {
                    for(Player p : finalBossBar.getPlayers()) p.stopSound(bossMob.getBossTheme(),SoundCategory.AMBIENT);
                    finalBossBar.removeAll();
                    this.cancel();
                    return;
                }

                double currentHealth = boss.getHealth();
                double maxHealth = boss.getMaxHealth();
                double progress = Math.clamp(currentHealth / maxHealth, 0.0, 1.0);

                if(boss.getHealth() <= (boss.getMaxHealth() * 0.5)) finalBossBar.setTitle(
                        "§8• §k|§f" + org.blackstamp.sleepychronicles.global.utils.color.ChatColor.of(hexColor) + " " + halfHealthTitle + " " + "§8§k|§f §8•");
                finalBossBar.setProgress(progress);

                Location bossLoc = bukkitBoss.getLocation();
                Collection<Player> playersInRange = bossLoc.getNearbyPlayers(50);
                Set<Player> currentViewers = new HashSet<>(finalBossBar.getPlayers());

                for(org.bukkit.entity.Player viewer : currentViewers) {

                    if(!playersInRange.contains(viewer)){
                        finalBossBar.removePlayer(viewer);

                        viewer.stopSound(bossMob.getBossTheme(),SoundCategory.AMBIENT);
                    }
                }

                for(org.bukkit.entity.Player p : playersInRange){
                    if(!currentViewers.contains(p)){
                        finalBossBar.addPlayer(p);

                        p.playSound(p, bossMob.getBossTheme(),SoundCategory.AMBIENT, 1.0F,1.0F);

                        scheduleThemeEnd(bossMob, p);
                    }
                }

                if(playersInRange.isEmpty()){
                    finalBossBar.removeAll();
                    bukkitBoss.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(SleepyChronicles.getInstance(), 0, 20);
    }

    private void scheduleThemeEnd(bossMob boss, Player p){
        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            if(!boss.getEntity().isAlive()) return;
            if(p.isDead()) return;

            p.playSound(p, boss.getBossTheme(),SoundCategory.AMBIENT, 1.0F,1.0F);

            scheduleThemeEnd(boss, p);
        }, boss.getThemeDurationTicks());
    }

    private BossBar createBossbar(String barTitle, BarColor barColor, String hexColor) {
        return Bukkit.createBossBar(
                "§8• §k|§f" + org.blackstamp.sleepychronicles.global.utils.color.ChatColor.of(hexColor) + " " + barTitle + " " + "§8§k|§f §8•",
                barColor,
                BarStyle.SEGMENTED_12
        );
    }

    public HashMap<String, String> getDamageSources() {
        HashMap<String, String> damageSources = new HashMap<>();

        damageSources.put("ENTITY_ATTACK", "Attack from ");
        damageSources.put("ENTITY_EXPLOSION", "Blewed up by a ");
        damageSources.put("BLOCK_EXPLOSION", "Block Explosion");
        damageSources.put("CONTACT", "Contact");
        damageSources.put("DROWNING", "Drowning");
        damageSources.put("SUFFOCATION", "Suffocating");
        damageSources.put("FALL", "Falling");
        damageSources.put("THORNS", "Thorns");
        damageSources.put("FIRE", "Fire");
        damageSources.put("FIRE_TICK", "Fire ticks");
        damageSources.put("LAVA", "Lava");
        damageSources.put("LIGHTNING", "Lightning");
        damageSources.put("POISON", "Poisoning");
        damageSources.put("WITHER", "Withering");
        damageSources.put("PROJECTILE", "Projectile");
        damageSources.put("MAGIC", "Magic");
        damageSources.put("VOID", "Void");
        damageSources.put("STARVATION", "Hunger");
        damageSources.put("KILL", "Suicide");
        damageSources.put("WORLD_BORDER", "Reached the limits.. quite literally");
        damageSources.put("CUSTOM", "Intentional Plugin-Design");
        damageSources.put("NULL", "Unknown");

        return damageSources;
    }
}
