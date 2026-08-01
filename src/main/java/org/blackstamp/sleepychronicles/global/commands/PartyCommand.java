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
public class PartyCommand extends BaseCommand {

    @Subcommand("create")
    public void create(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        UUID uuid = p.getUniqueId();
        SleepyParty party = new SleepyParty(uuid);

        if(!PartyManager.hasParty(uuid)){
            PartyManager.addToParty(uuid,party);

            ChatManager.sendMessage(p, false,"Party created successfully!");
            return;
        }

        ChatManager.sendMessage(p, true,"You're already in a party!");
    }

    @Subcommand("invite")
    public void invite(CommandSender sender, Player receiver){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid) || receiver == null){
            ChatManager.sendMessage(p, true,"The invitation couldn't be sent.");
            return;
        }

        UUID receiverUUID = receiver.getUniqueId();

        if(!PartyManager.hasParty(receiverUUID)){
            SleepyParty party = PartyManager.getParty(uuid);
            int duration = 60;
            long expiration = System.currentTimeMillis() + (duration * 1000L); // 60s upon expiration.

            PartyManager.addPendingInvite(receiverUUID,uuid,new PartyInvite(party,expiration));

            ChatManager.sendMessage(p, true,"Invitation sent! (" + duration + "s)");
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
                ChatManager.sendMessage(p, false,"Joined the party of "+leaderName+"!");

            }else{
                PartyManager.removePendingInvite(uuid, leaderUUID);
                ChatManager.sendMessage(p, true,"The invitation expired.");
            }

            return;
        }

        ChatManager.sendMessage(p, true,"There were some errors with the invitation.");
    }

    @Subcommand("disband")
    public void disband(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        UUID uuid = p.getUniqueId();

        if(PartyManager.hasParty(uuid)){
            SleepyParty party = PartyManager.getParty(uuid);

            if(PartyManager.isLeader(uuid,party)){
                party.disbandParty();
                PartyManager.removeParty(party);

                ChatManager.sendMessage(p, false,"Party disbanded!");
                return;
            }

            ChatManager.sendMessage(p, true,"Only the leader may disband the party!");
            return;
        }

        ChatManager.sendMessage(p, true,"You don't have a party...");
    }

    @Subcommand("kick")
    public void kick(CommandSender sender, Player kicked){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();
        UUID kickedUUID = kicked.getUniqueId();

        if(PartyManager.hasParty(uuid)){
            SleepyParty party = PartyManager.getParty(uuid);

            if(PartyManager.isLeader(uuid,party)){
                String kickedName = Bukkit.getOfflinePlayer(kickedUUID).getName();

                if(!party.hasMember(kickedUUID)){
                    ChatManager.sendMessage(p, true,kickedName + " doesn't belong to the party!");
                    return;
                }

                party.removeMember(kickedUUID);
                PartyManager.removeFromParty(kickedUUID);

                for(UUID memberUUID : party.getMembers()){
                    Player member = Bukkit.getPlayer(memberUUID);

                    if(member == null || !member.isOnline()) continue;

                    ChatManager.sendMessage(member, false,kickedName + "has been kicked from the party!");
                }

                return;
            }

            ChatManager.sendMessage(p, true,"Only the leader may kick members from the party!");
            return;
        }

        ChatManager.sendMessage(p, true,"You don't have a party...");
    }
}
