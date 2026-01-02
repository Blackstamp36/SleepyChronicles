package org.blackstamp.sleepychronicles;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.commands.StaffCommand;
import org.blackstamp.sleepychronicles.deprecated.recipe.recipeRegister;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntityRegistry;
import org.blackstamp.sleepychronicles.global.utils.registrable.RegistrableUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SleepyChronicles extends JavaPlugin {
    NMSEntityRegistry nER = new NMSEntityRegistry();
    recipeRegister recipes = new recipeRegister();
    public static String chatPrefix = "§7§l[§r" + ChatColor.of("#6932a8") + "SʟᴇᴇᴘʏCʜʀᴏɴɪᴄʟᴇꜱ§7§l]§r §8» ";
    @Getter
    private static SleepyChronicles instance;
    GlobalClass global = new GlobalClass();

    public static int serverDay = 1;

    @Override
    public void onEnable() {
        instance = this;

        executeGlobalClassMethods();

        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new StaffCommand());

        recipes.registerRecipes();

        Bukkit.getConsoleSender().sendMessage("S̸̝̈́͐̍͛̓̆͛͘͝͠l̸͇͕̤͒̄͐̋̒͝e̸̛̛̓͗͊̈̔̊͒͜ḛ̸̖̗̒͋̎̇͆͘͠ṕ̵̪͎͚̪͚̲̱̎̋̔͒̍̎͐ͅy̵̧̡̳͉̹̞͉̙͙͌̍̚͜ ̴͎̀̽͠ͅC̴̖͖̘͚̿͊͋̄̈́̀h̸̢̺̪̣̳̟̘̠̓̂͘r̴͉̐͒͆͛͝ǫ̸̨̜͍̹̞͚̙̩͂͂ṉ̵̺͚̪̹̔̓́̊̅͜͝ǐ̸͈̼̻̈͘c̵̢͍̥̦͓̤͖̍̅̎̆̓̈́͘l̴̪̅̐̓̒́̃͆̋̐͐e̸͚̭͇̠͋̐̽̌̾̎̕͝ͅs̵̢̟̲̤̦̟̠̿̽̽̉̚");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(instance.getName() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(instance.getName() + " has been disabled.");
    }

    private void executeGlobalClassMethods(){
        global.createMainFiles();
        global.initDiscordBot();
        global.initPlayerTasks();
        global.initChangeDayTask();
        global.registerPlayerTeams();
        global.createAftermathDimension();
        RegistrableUtils.registerListeners();
        nER.scanNMSClasses();
    }

}




