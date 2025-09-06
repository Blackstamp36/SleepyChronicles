package org.blackstamp.sleepyChronicles;

import org.blackstamp.sleepyChronicles.command.onStaffTab;
import org.blackstamp.sleepyChronicles.command.sleepyCRegister;
import org.blackstamp.sleepyChronicles.command.onSleepyTab;
import org.blackstamp.sleepyChronicles.command.staffCRegister;
import org.blackstamp.sleepyChronicles.recipe.recipeRegister;
import org.blackstamp.sleepyChronicles.util.RegistrableUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class sleepyChronicles extends JavaPlugin {
    recipeRegister recipes = new recipeRegister();
    public static String chatPrefix = "§7§l[§r" + org.blackstamp.sleepyChronicles.util.ChatColor.of("#6932a8") + "SʟᴇᴇᴘʏCʜʀᴏɴɪᴄʟᴇꜱ§7§l]§r §8» ";
    private static sleepyChronicles instance;
    globalClass global = new globalClass();
    public static int serverDay = 1;

    @Override
    public void onEnable() {
        instance = this;

        executeGlobalClassMethods();

        registerCommands();
        recipes.registerRecipes();

        System.out.println("S̸̝̈́͐̍͛̓̆͛͘͝͠l̸͇͕̤͒̄͐̋̒͝e̸̛̛̓͗͊̈̔̊͒͜ḛ̸̖̗̒͋̎̇͆͘͠ṕ̵̪͎͚̪͚̲̱̎̋̔͒̍̎͐ͅy̵̧̡̳͉̹̞͉̙͙͌̍̚͜ ̴͎̀̽͠ͅC̴̖͖̘͚̿͊͋̄̈́̀h̸̢̺̪̣̳̟̘̠̓̂͘r̴͉̐͒͆͛͝ǫ̸̨̜͍̹̞͚̙̩͂͂ṉ̵̺͚̪̹̔̓́̊̅͜͝ǐ̸͈̼̻̈͘c̵̢͍̥̦͓̤͖̍̅̎̆̓̈́͘l̴̪̅̐̓̒́̃͆̋̐͐e̸͚̭͇̠͋̐̽̌̾̎̕͝ͅs̵̢̟̲̤̦̟̠̿̽̽̉̚");
        System.out.println();
        System.out.println("Good morning! =)");
    }

    @Override
    public void onDisable() {
        System.out.println("It's time to sleep, my dear sleepy chronicle.. ");
    }

    private void registerCommands() {
        // Registers the commands from the plugin.

        this.getCommand("sc").setExecutor(new sleepyCRegister());
        this.getCommand("staff").setExecutor(new staffCRegister());
        this.getCommand("sc").setTabCompleter(new onSleepyTab());
        this.getCommand("staff").setTabCompleter(new onStaffTab());
    }

    public static sleepyChronicles getter() {
        return instance;
    }

    private void executeGlobalClassMethods(){
        global.createMainFiles();
        global.initDiscordBot();
        global.initPlayerTasks();
        global.initChangeDayTask();
        global.registerPlayerTeams();
        global.createAftermathDimension();
        RegistrableUtils.registerListeners();
    }

}




