package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.party.PartyInvite;
import org.blackstamp.sleepychronicles.api.party.PartyManager;
import org.blackstamp.sleepychronicles.api.party.SleepyParty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

        if(PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(p, true,"You're already in a party!");
            return;
        }

        PartyManager.addToParty(uuid,party);

        ChatManager.sendMessage(p, false,"Party created successfully! (/p invite)");
    }

    @Subcommand("invite")
    public void invite(CommandSender sender, String target){
        if(!(sender instanceof Player p)) return;

        UUID leaderUUID = p.getUniqueId();

        if(!PartyManager.hasParty(leaderUUID)){
            ChatManager.sendMessage(p, true,"You need to create a party first!");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);

        if(targetPlayer == null){
            ChatManager.sendMessage(p, true,"The player is not online!");
            return;
        }

        UUID targetUUID = targetPlayer.getUniqueId();

        if(PartyManager.hasParty(targetUUID)){
            ChatManager.sendMessage(p, true,"The player's already in a party!");
            return;
        }

        SleepyParty party = PartyManager.getParty(leaderUUID);
        int duration = 60;
        long expiration = System.currentTimeMillis() + (duration * 1000L); // 60s upon expiration.

        PartyManager.addPendingInvite(targetUUID, leaderUUID,new PartyInvite(party,expiration));

        ChatManager.sendMessage(p, true,"Invitation sent! (" + duration + "s)");
    }

    @Subcommand("accept")
    public void accept(CommandSender sender, String leader){
        if(!(sender instanceof Player p)) return;

        UUID uuid = p.getUniqueId();

        OfflinePlayer leaderPlayer = Bukkit.getPlayer(leader);

        if(leaderPlayer == null){
            ChatManager.sendMessage(p, true,"The party that you tried to join doesn't exist.");
            return;
        }

        UUID leaderUUID = leaderPlayer.getUniqueId();

        if(PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(p, true,"You're already in a party.");
            return;
        }

        PartyInvite invite = PartyManager.getPendingInvite(uuid,leaderUUID);

        if(invite == null || !PartyManager.hasPendingInvite(uuid, leaderUUID)){
            ChatManager.sendMessage(p, true,"The invitation doesn't exist.");
            return;
        }

        SleepyParty party = invite.targetParty();

        if(party.getMembers().size() >= 10){ // I think I'm gonna erase this. But just for you to see.
            ChatManager.sendMessage(p, true,"The party is full!"); // Because I don't want to depend on the command
            return; // for the size of a party. I want that it depends of the dungeon that is being played.
        }

        if(System.currentTimeMillis() > invite.expirationTime()){
            PartyManager.removePendingInvite(uuid, leaderUUID);
            ChatManager.sendMessage(p, true,"The invitation expired.");
            return;
        }

        String leaderName = leaderPlayer.getName();

        PartyManager.removePendingInvite(uuid, leaderUUID);
        PartyManager.addToParty(uuid,party);
        party.addMember(uuid);

        ChatManager.sendMessage(p, false,"Joined the party of "+leaderName+"!");
    }

    @Subcommand("disband")
    public void disband(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(p, true,"You don't have a party. (/p create)");
            return;
        }

        SleepyParty party = PartyManager.getParty(uuid);

        if(!PartyManager.isLeader(uuid,party)){
            ChatManager.sendMessage(p, true,"Only the leader may disband the party!");
            return;
        }

        PartyManager.removeParty(party);
        party.disbandParty();
        ChatManager.sendMessage(p, false,"Party disbanded!");
    }

    @Subcommand("kick")
    public void kick(CommandSender sender, String target){
        if(!(sender instanceof Player leader)) return;

        UUID leaderUUID = leader.getUniqueId();
        UUID targetUUID = null;

        if(!PartyManager.hasParty(leaderUUID)){
            ChatManager.sendMessage(leader, true,"You don't have a party. (/p create)");
            return;
        }

        SleepyParty party = PartyManager.getParty(leaderUUID);

        if(!PartyManager.isLeader(leaderUUID,party)){
            ChatManager.sendMessage(leader, true,"Only the leader may kick off members!");
            return;
        }

        for(UUID member : party.getMembers()){
            OfflinePlayer offlineMember = Bukkit.getOfflinePlayer(member);
            String offlineName = offlineMember.getName();

            if(offlineName != null && offlineName.equalsIgnoreCase(target)){ // UUID found!
                targetUUID = member;
                break;
            }
        }

        if(targetUUID == null){
            ChatManager.sendMessage(leader, true,"The player wasn't found in the party.");
            return;
        }

        if(targetUUID.equals(leaderUUID)){
            ChatManager.sendMessage(leader, true,"You cannot.. kick yourself from your party.. try (/p disband)");
            return;
        }

        // If we reached here, it means that the UUID is valid!
        party.removeMember(targetUUID);
        PartyManager.removeFromParty(targetUUID);

        Player targetPlayer = Bukkit.getPlayer(targetUUID);

        // We reutilize the 'target' var that we had previously declared.
        target = Bukkit.getOfflinePlayer(targetUUID).getName();

        for(UUID uuid : party.getMembers()){
            Player onlineMember = Bukkit.getPlayer(uuid);

            if(onlineMember == null) continue;

            ChatManager.sendMessage(onlineMember, false,target + "has been kicked from the party!");
            }

        if(targetPlayer != null && targetPlayer.isOnline()){
            ChatManager.sendMessage(targetPlayer, true,"You were kicked from your party!");
            }
        }
    }
