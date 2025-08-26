package org.blackstamp.sleepyChronicles.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onSleepyTab implements TabCompleter {
    public final String[] values = {
            "day", "totem", "totems", "gettotems", "trinkets"
    };

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        final List<String> completions = new ArrayList<>(Arrays.asList(values));
        StringUtil.copyPartialMatches(args[0], List.of(values), completions);

        return completions;
    }
}
