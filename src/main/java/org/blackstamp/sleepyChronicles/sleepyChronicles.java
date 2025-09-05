package org.blackstamp.sleepyChronicles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.blackstamp.sleepyChronicles.command.onStaffTab;
import org.blackstamp.sleepyChronicles.command.sleepyCRegister;
import org.blackstamp.sleepyChronicles.command.onSleepyTab;
import org.blackstamp.sleepyChronicles.command.staffCRegister;
import org.blackstamp.sleepyChronicles.dimension.AftermathBiomeProvider;
import org.blackstamp.sleepyChronicles.dimension.AftermathChunkGenerator;
import org.blackstamp.sleepyChronicles.recipe.recipeRegister;
import org.blackstamp.sleepyChronicles.util.RegistrableUtils;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class sleepyChronicles extends JavaPlugin {
    recipeRegister recipes = new recipeRegister();
    public static String PREFIX = "§7§l[§r" + org.blackstamp.sleepyChronicles.util.ChatColor.of("#6932a8") + "SʟᴇᴇᴘʏCʜʀᴏɴɪᴄʟᴇꜱ§7§l]§r §8» ";
    private static sleepyChronicles instance;
    public static File pluginDir = new File("plugins", "sleepyChronicles");
    globalClass global = new globalClass();
    public static int serverDay = 1;

    @Override
    public void onEnable() {
        instance = this;

        global.globalData.put("days", global.getServerDay());
        global.initializeDiscordBot();
        createMainFiles();
        createAftermathDimension();
        RegistrableUtils.registerListeners();
        registerTeams();
        registerCommands();
        recipes.registerRecipes();
        changeDaySystem();
        System.out.println("S̸̝̈́͐̍͛̓̆͛͘͝͠l̸͇͕̤͒̄͐̋̒͝e̸̛̛̓͗͊̈̔̊͒͜ḛ̸̖̗̒͋̎̇͆͘͠ṕ̵̪͎͚̪͚̲̱̎̋̔͒̍̎͐ͅy̵̧̡̳͉̹̞͉̙͙͌̍̚͜ ̴͎̀̽͠ͅC̴̖͖̘͚̿͊͋̄̈́̀h̸̢̺̪̣̳̟̘̠̓̂͘r̴͉̐͒͆͛͝ǫ̸̨̜͍̹̞͚̙̩͂͂ṉ̵̺͚̪̹̔̓́̊̅͜͝ǐ̸͈̼̻̈͘c̵̢͍̥̦͓̤͖̍̅̎̆̓̈́͘l̴̪̅̐̓̒́̃͆̋̐͐e̸͚̭͇̠͋̐̽̌̾̎̕͝ͅs̵̢̟̲̤̦̟̠̿̽̽̉̚");
        System.out.println();
        System.out.println("Enabling " + sleepyChronicles.getter().getName() + "!");
        global.initPlayerTasks();
    }

    @Override
    public void onDisable() {
        System.out.println("It's time to sleep, my dear sleepy chronicle.. ");

    }

    private void registerTeams() {
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

    private void registerCommands() {
        // Registers the commands from the plugin

        this.getCommand("sc").setExecutor(new sleepyCRegister());
        this.getCommand("staff").setExecutor(new staffCRegister());
        this.getCommand("sc").setTabCompleter(new onSleepyTab());
        this.getCommand("staff").setTabCompleter(new onStaffTab());
    }

    private void changeDaySystem() {
        Bukkit.getScheduler().runTaskTimer(sleepyChronicles.getter(), () -> {
            global.setServerDay(global.getServerDay() + 1);
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false, false));
                all.sendTitle("§7§k|§r §5Day passed! §7§k|", "§7Day §c" + (global.getServerDay() - 1) + " §6→→ " + "§7Day §c" + global.getServerDay() + "§7!");
                all.playSound(all, Sound.ENTITY_RAVAGER_DEATH, 1, 0.5F);
            });

        }, 1728000, 1728000); //1728000 ticks = 1 IRL day
    }

    public static sleepyChronicles getter() {
        return instance;
    }

    private void createMainFiles() {
        File file = new File("plugins/" + sleepyChronicles.getter().getName(), "mainFile.json");
        globalClass global = new globalClass();

        File schematics = new File("plugins/" + sleepyChronicles.getter().getName(), "schematics");
        if(!schematics.exists()) {
            schematics.mkdir();
            System.out.println("No dir found for schematics! Creating new one..");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!pluginDir.exists()) {
            pluginDir.mkdir();
            System.out.println("No dir found for " + sleepyChronicles.getter().getName() + " plugin! Creating new one..");
        }

        if (file.exists()) return;

        global.globalData.put("days", serverDay);

        try (FileWriter fileW = new FileWriter(file)) {
            gson.toJson(global.globalData, fileW);

        } catch (IOException e) {
            System.out.println("An exception has ocurred in createData! " + e.getMessage());
        }
    }

    private void createAftermathDimension() {
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

}




