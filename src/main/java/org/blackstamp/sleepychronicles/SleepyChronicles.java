package org.blackstamp.sleepychronicles;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.blackstamp.sleepychronicles.api.cooldown.CooldownManager;
import org.blackstamp.sleepychronicles.api.data.days.DayManager;
import org.blackstamp.sleepychronicles.api.item.ItemAbility;
import org.blackstamp.sleepychronicles.api.item.ItemUtils;
import org.blackstamp.sleepychronicles.api.item.SleepyItems;
import org.blackstamp.sleepychronicles.api.mobs.MobUtils;
import org.blackstamp.sleepychronicles.api.mobs.SleepyMobs;
import org.blackstamp.sleepychronicles.api.player.PlayerUtils;
import org.blackstamp.sleepychronicles.game.spawn.SpawnManager;
import org.blackstamp.sleepychronicles.global.commands.StaffCommand;
import org.blackstamp.sleepychronicles.global.utils.registrable.RegistrableUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SleepyChronicles extends JavaPlugin {

    @Getter private static SleepyChronicles instance;

    // GlobalClass global = new GlobalClass();

    @Override
    public void onEnable() {
        instance = this;

        loadFields();
        loadCommands();
        loadTasks();

        // global.createAftermathDimension();

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
        SpawnManager.getInstance().register();
    }

    private void loadCommands(){
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.getCommandCompletions().registerAsyncCompletion(
                "@SleepyMobs", c -> SleepyMobs.getIDs());
        paperCommandManager.getCommandCompletions().registerAsyncCompletion(
                "@PlayersOnline", c -> PlayerUtils.getOnlinePlayers());
        paperCommandManager.registerCommand(new StaffCommand());
    }

    private void loadTasks(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for(Player p : Bukkit.getOnlinePlayers()){
                    for(ItemStack piece : p.getInventory().getArmorContents()){

                        if(piece == null) continue;

                        String id = ItemUtils.getID(piece.getItemMeta());

                        if(id == null) continue;

                        ItemAbility ability = SleepyItems.getAbility(id);
                        if(ability != null) ability.onArmorTick(p);
                    }
                }
            }
        }.runTaskTimer(this,0L,20L);
    }
}