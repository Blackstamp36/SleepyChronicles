package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.dungeon.PartyInvite;
import org.blackstamp.sleepychronicles.api.dungeon.PartyManager;
import org.blackstamp.sleepychronicles.api.dungeon.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("p|party")
public class PartyCommand extends BaseCommand {

    @Subcommand("create")
    public void create(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        SleepyParty party = new SleepyParty(p.getUniqueId());

        PartyManager.addToParty(p.getUniqueId(),party);

        ChatManager.sendStaffMessage(p, "Party created!");
    }

    @Subcommand("invite")
    public void invite(CommandSender sender, Player receiver){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid) || receiver == null){
            ChatManager.sendStaffMessage(p, "The invitation couldn't be sent.");
            return;
        }

        UUID receiverUUID = receiver.getUniqueId();

        if(!PartyManager.hasParty(receiverUUID)){
            SleepyParty party = PartyManager.getParty(uuid);
            int duration = 60;
            long expiration = System.currentTimeMillis() + (duration * 1000L); // 60s upon expiration.

            PartyManager.addPendingInvite(receiverUUID, new PartyInvite(party,expiration));

            ChatManager.sendStaffMessage(p, "Invitation sent! (" + duration + "s)");

        }
    }

    @Subcommand("accept")
    public void accept(CommandSender sender, Player target){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid) && PartyManager.hasPendingInvite(uuid)){
            PartyInvite invite = PartyManager.getPendingInvite(uuid);
            SleepyParty party = invite.targetParty();

            if(System.currentTimeMillis() <= invite.expirationTime()){
                if(party.getMembers().size() >= 4) return;

                String leaderName = Bukkit.getPlayer(party.getLeader()).getName();

                PartyManager.addToParty(uuid,party);
                party.addMember(uuid);
                ChatManager.sendStaffMessage(p, "Joined the party of "+leaderName+"!");

            }else{
                PartyManager.removePendingInvite(uuid);
                ChatManager.sendStaffMessage(p, "The invitation expired.");
            }
        }

        ChatManager.sendStaffMessage(p, "There were some errors with the invitation.");
    }
}
