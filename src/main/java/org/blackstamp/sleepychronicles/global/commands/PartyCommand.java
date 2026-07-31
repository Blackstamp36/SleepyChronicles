package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.party.PartyInvite;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("p|party")
public class PartyCommand extends BaseCommand { // todo: add other essential party commands!

    @Subcommand("create")
    public void create(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        UUID uuid = p.getUniqueId();
        SleepyParty party = new SleepyParty(uuid);

        if(!PartyManager.hasParty(uuid)){
            PartyManager.addToParty(uuid,party);

            ChatManager.sendStaffMessage(p, "Party created successfully!");
            return;
        }

        ChatManager.sendStaffMessage(p, "You're already in a party!");
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

            PartyManager.addPendingInvite(receiverUUID,uuid,new PartyInvite(party,expiration));

            ChatManager.sendStaffMessage(p, "Invitation sent! (" + duration + "s)");
        }
    }

    @Subcommand("accept")
    public void accept(CommandSender sender, Player leader){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();
        UUID leaderUUID = leader.getUniqueId();

        if(!PartyManager.hasParty(uuid) && PartyManager.hasPendingInvite(uuid,leaderUUID)){
            PartyInvite invite = PartyManager.getPendingInvite(uuid,leaderUUID);

            if(invite == null) return;

            SleepyParty party = invite.targetParty();

            if(System.currentTimeMillis() <= invite.expirationTime()){
                if(party.getMembers().size() >= 4) return;

                String leaderName = Bukkit.getOfflinePlayer(party.getLeader()).getName();

                PartyManager.removePendingInvite(uuid, leaderUUID);
                PartyManager.addToParty(uuid,party);
                party.addMember(uuid);
                ChatManager.sendStaffMessage(p, "Joined the party of "+leaderName+"!");

            }else{
                PartyManager.removePendingInvite(uuid, leaderUUID);
                ChatManager.sendStaffMessage(p, "The invitation expired.");
            }

            return;
        }

        ChatManager.sendStaffMessage(p, "There were some errors with the invitation.");
    }
}
