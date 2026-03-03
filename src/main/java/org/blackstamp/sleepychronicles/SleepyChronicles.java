package org.blackstamp.sleepychronicles;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.blackstamp.sleepychronicles.api.cooldown.CooldownManager;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.mobs.MobUtils;
import org.blackstamp.sleepychronicles.api.player.PlayerUtils;
import org.blackstamp.sleepychronicles.game.spawn.SpawnManager;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.commands.StaffCommand;
import org.blackstamp.sleepychronicles.global.utils.registrable.RegistrableUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SleepyChronicles extends JavaPlugin {

    @Getter private static SleepyChronicles instance;

    GlobalClass global = new GlobalClass();

    @Override
    public void onEnable() {
        instance = this;

        loadFields();
        loadCommands();

        global.createAftermathDimension();

        Bukkit.getConsoleSender().sendMessage("S̸̝̈́͐̍͛̓̆͛͘͝͠l̸͇͕̤͒̄͐̋̒͝e̸̛̛̓͗͊̈̔̊͒͜ḛ̸̖̗̒͋̎̇͆͘͠ṕ̵̪͎͚̪͚̲̱̎̋̔͒̍̎͐ͅy̵̧̡̳͉̹̞͉̙͙͌̍̚͜ ̴͎̀̽͠ͅC̴̖͖̘͚̿͊͋̄̈́̀h̸̢̺̪̣̳̟̘̠̓̂͘r̴͉̐͒͆͛͝ǫ̸̨̜͍̹̞͚̙̩͂͂ṉ̵̺͚̪̹̔̓́̊̅͜͝ǐ̸͈̼̻̈͘c̵̢͍̥̦͓̤͖̍̅̎̆̓̈́͘l̴̪̅̐̓̒́̃͆̋̐͐e̸͚̭͇̠͋̐̽̌̾̎̕͝ͅs̵̢̟̲̤̦̟̠̿̽̽̉̚");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(instance.getName() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(instance.getName() + " has been disabled.");
    }

    private void loadFields(){
        new DayManager();
        new CooldownManager();
        new SpawnManager();

        RegistrableUtils.registerListeners();
        MobUtils.initializeMobConstructors();
        SpawnManager.getInstance().register();
    }

    private void loadCommands(){
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.getCommandCompletions().registerAsyncCompletion(
                "@SleepyMobs", _ -> MobUtils.getMobNames());
        paperCommandManager.getCommandCompletions().registerAsyncCompletion(
                "@PlayersOnline", _ -> PlayerUtils.getOnlinePlayers());
        paperCommandManager.registerCommand(new StaffCommand());
    }
}