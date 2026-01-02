package org.blackstamp.sleepychronicles.deprecated.commands;

import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntityRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onStaffTab implements TabCompleter {
    private final NMSEntityRegistry nER;

    public onStaffTab(NMSEntityRegistry nER) {
        this.nER = nER;
    }

    GlobalClass global = new GlobalClass();

    private final String[] values = {
            "items", "settotems", "setday", "summon", "teleport", "broadcast", "storm", "entities", "chest"
    };

    private final String[] storm = {"start", "stop"};
    private final String[] worlds = global.getServerWorlds().keySet().toArray(new String[0]);

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        final String[] mobs = nER.getNMSEntitiesMap().keySet().toArray(new String[0]);

        final List<String> vCompletions = new ArrayList<>(Arrays.asList(values));
        final List<String> mCompletions = new ArrayList<>(Arrays.asList(mobs));
        final List<String> wCompletions = new ArrayList<>(Arrays.asList(worlds));

        if (sender.isOp()) {
            if (args.length == 1) {
                return vCompletions;

            } else if (args.length == 2) {
                switch(args[0].toUpperCase()){
                    case "SUMMON" -> {
                        return mCompletions;
                    }

                    case "SETTOTEMS" -> {
                        return new ArrayList<>(List.of("<int>"));
                    }

                    case "TELEPORT" -> {
                        return wCompletions;
                    }

                    case "STORM" -> {
                        return new ArrayList<>(List.of(storm));
                    }

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
