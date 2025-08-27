package org.blackstamp.sleepyChronicles;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.blackstamp.sleepyChronicles.item.trinkets.trinketItems;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creaking.bobCreaking;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.missingId;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.creeper.suppressedCreeper;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.enderman.nightMan;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.endermite.netherMite;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.fox.kitsuneFox;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.ghast.eyelessGhast;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.iron_golem.quantumGolem;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.llama.aggresiveLlama;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.phantom.seekerPhantom;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.skeleton.banditSkeleton;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.slime.seedGhostSlime;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.wither_boss.mechanicalEye;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.spider.voidbornSpider;
import org.blackstamp.sleepyChronicles.nms.v1_21_5_R01.entity.zombie.paleSoul;
import org.blackstamp.sleepyChronicles.util.adapter.ItemStackTypeAdapter;
import org.blackstamp.sleepyChronicles.util.adapter.ListItemStackTypeAdapter;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static com.mojang.logging.LogUtils.getLogger;
import static org.blackstamp.sleepyChronicles.sleepyChronicles.pluginDir;

public class globalClass {
    @Getter
    public static final Map<UUID, Map<String, Long>> activeCooldowns = new HashMap<>();

    public HashMap<String, Integer> globalData = new HashMap<>();
    public HashMap<UUID, PickaxeMode> playerPickaxes = new HashMap<>();
    public HashMap<UUID, Boolean> pickaxesCooldowns = new HashMap<>();
    public enum PickaxeMode {
        SILK, FORTUNE
    }

    public void createPlayerData(UUID uuid) {
        File playerFile = getPlayerFile(uuid);
        playerFile.getParentFile().mkdirs();

        if (playerFile.exists()) {
            return;
        }

        playerData newData = new playerData();

        try (FileWriter writer = new FileWriter(playerFile)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                    .registerTypeAdapter(new TypeToken<List<ItemStack>>(){}.getType(), new ListItemStackTypeAdapter())
                    .create();
            gson.toJson(newData, writer);
            getLogger().info("New data for player: " + uuid);
        } catch (IOException e) {
            getLogger().warn("Couldn't create file for " + uuid + ": " + e.getMessage());
        }
    }

    public void savePlayerData(UUID uuid, playerData data) {
        File playerFile = getPlayerFile(uuid);

        try (FileWriter writer = new FileWriter(playerFile)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                    .registerTypeAdapter(new TypeToken<List<ItemStack>>(){}.getType(), new ListItemStackTypeAdapter())
                    .create();

            gson.toJson(data, writer);
        } catch (Exception e) {
            System.out.println("[DEBUG] ERROR DURING SAVE:");
            e.printStackTrace();
        }
    }

    public playerData getPlayerData(UUID uuid) {
        File playerFile = getPlayerFile(uuid);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter())
                .registerTypeAdapter(new TypeToken<List<ItemStack>>(){}.getType(), new ListItemStackTypeAdapter())
                .create();

        if (!playerFile.exists()) {
            createPlayerData(uuid);
            return new playerData();
        }

        try (FileReader reader = new FileReader(playerFile)) {
            return gson.fromJson(reader, playerData.class);

        } catch (Exception e) {
            getLogger().warn("Couldn't load data for " + uuid + ": " + e.getMessage());
            return new playerData();
        }
    }

    public void setTotems(UUID uuid, int totemsToSet) {
        playerData data = getPlayerData(uuid);
        data.setTotems(totemsToSet);
        savePlayerData(uuid, data);
    }

    public void updateTotems(UUID uuid, int totemsToAdd) {
        playerData data = getPlayerData(uuid);
        data.setTotems(data.getTotems() + totemsToAdd);
        savePlayerData(uuid, data);
    }

    public int getTotems(UUID uuid) {
        playerData data = getPlayerData(uuid);
        return data.getTotems();
    }

    public void updateTrinkets(UUID uuid, Inventory inv) {
        playerData data = getPlayerData(uuid);
        data.setTrinketsFromInventory(inv);
        savePlayerData(uuid, data);
    }

    private File getPlayerFile(UUID uuid){
        return new File("plugins/" + sleepyChronicles.getter().getName() + "/" + uuid, uuid + ".json");
    }

    private File getMainFile(){
        return new File("plugins/" + sleepyChronicles.getter().getName(), "mainFile.json");
    }

    public int getServerDay() {
        Gson gson = new Gson();
        File file = new File("plugins/" + sleepyChronicles.getter().getName(), "mainFile.json");

        if (!file.exists()) {
            System.out.println("File " + file.getName() + " not found! Returning..");
        }

        try (FileReader fileR = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> data = gson.fromJson(fileR, type);

            if (data.get("days") != null) {
                globalData.put("days", data.get("days"));
                return globalData.get("days");

            }
        } catch (Exception e) {
            System.out.println("An exception has ocurred in getTotems! " + e.getMessage());
        }

        return 1;
    }

    public void setServerDay(int day) {
        File file = new File("plugins/" + sleepyChronicles.getter().getName(), "mainFile.json");
        globalData.put("days", day);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!pluginDir.exists()) {
            pluginDir.mkdir();
            System.out.println("No dir found for " + sleepyChronicles.getter().getName() + " plugin! Creating new one..");
        }

        try (FileWriter fileW = new FileWriter(file)) {
            gson.toJson(globalData, fileW);

        } catch (IOException e) {
            System.out.println("An exception has ocurred in createData! " + e.getMessage());
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

        if(material != null){
            pBuilder.data(material.createBlockData());
        }

        if(count != null){
            pBuilder.count(count);
        }

        pBuilder.spawn();
    }

    public void teleportRandom(Entity e, double radius){
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
        entityRegistry.put("PALESOUL", paleSoul.class);

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

    public void initPlayerTasks(){
        final Map<UUID, Boolean> hasNullTNT = new HashMap<>();
        final Map<UUID, Boolean> hasBobSoul = new HashMap<>();
        final Map<UUID, Boolean> hasQuantumCore = new HashMap<>();
        final Map<UUID, Boolean> hasQuantumReactor = new HashMap<>();
        final Map<UUID, Boolean> hasBobMiracle = new HashMap<>();

        trinketItems trinkets = new trinketItems();

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    checkImperceptibility(all);

                    UUID uuid = all.getUniqueId();
                    globalClass global = new globalClass();

                    AttributeInstance maxHealthAttr = all.getAttribute(Attribute.MAX_HEALTH);

                    playerData data = global.getPlayerData(uuid);
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
                    double modification = 0.0;

                    if (hasNullTNT.get(uuid)) {
                        modification -= 2.0;
                    }

                    if (hasBobSoul.get(uuid)) {
                        modification += 4.0;
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1, true, false));
                        all.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 0, true, false));
                    }

                    if(hasQuantumCore.get(uuid)){
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1, true, false));
                    }

                    if(hasQuantumReactor.get(uuid)){
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, true, false));
                    }

                    if (hasBobMiracle.get(uuid)) {
                        modification += 8.0;
                    }

                    all.setMaxHealth(baseHealth + modification);
                }
            }
        }.runTaskTimer(sleepyChronicles.getter(), 0, 60);
    }

    private void checkImperceptibility(Player p){
        if(p.hasPotionEffect(PotionEffectType.WEAVING)){
            for(Player all : Bukkit.getOnlinePlayers()){
                all.hidePlayer(p);
            }
        } else {
            for(Player all : Bukkit.getOnlinePlayers()){
                all.showPlayer(p);
            }
        }

    }

    public void removeTotemEffects(Player p){
        Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600,2));
        }, 1);
    }

    public void createTomb(Player p){
        Location pL = p.getLocation();

        Location headLoc = new Location(p.getWorld(), pL.getX(), pL.getY() + 1, pL.getZ());
        Location fenceLoc = new Location(p.getWorld(), pL.getX(), pL.getY(), pL.getZ());
        Location bedrockLoc = new Location(p.getWorld(), pL.getX(), pL.getY() - 1, pL.getZ());

        Material head = Material.PLAYER_HEAD;
        Material fence = Material.NETHER_BRICK_FENCE;
        Material bedrock = Material.BEDROCK;

        headLoc.getBlock().setType(head);
        fenceLoc.getBlock().setType(fence);
        bedrockLoc.getBlock().setType(bedrock);

        Block headBlock = headLoc.getBlock();;

        if (headBlock.getState() instanceof Skull skullState) {
            skullState.setOwner(p.getName());
            skullState.update();
        }

    }

}
