package org.blackstamp.sleepyChronicles.command;

import org.blackstamp.sleepyChronicles.globalClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onStaffTab implements TabCompleter {
    globalClass global = new globalClass();

    public final String[] values = {
            "items", "settotems", "setday", "summon", "teleport", "broadcast"
    };

    public final String[] mobs = global.getCustomEntities().keySet().toArray(new String[0]);
    public final String[] worlds = global.getServerWorlds().keySet().toArray(new String[0]);

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        final List<String> vCompletions = new ArrayList<>(Arrays.asList(values));
        final List<String> mCompletions = new ArrayList<>(Arrays.asList(mobs));
        final List<String> wCompletions = new ArrayList<>(Arrays.asList(worlds));

        if (sender.isOp()) {
            if (args.length == 1) {
                return vCompletions;

            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("summon")) {
                    return mCompletions;

                } else if(args[0].equalsIgnoreCase("settotems")){
                    return new ArrayList<>(List.of("<int>"));

                } else if(args[0].equalsIgnoreCase("teleport")){
                    return wCompletions;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("summon")) {
                    return new ArrayList<>(List.of("<int>"));
                }
            }
            return new ArrayList<>(List.of(""));
        }

        return new ArrayList<>(List.of(""));
    }
}
