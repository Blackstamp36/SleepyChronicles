package org.blackstamp.sleepychronicles.global.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import org.blackstamp.sleepychronicles.api.chat.ChatManager;
import org.blackstamp.sleepychronicles.api.color.BasicPalette;
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
    @CommandCompletion("@PlayersOnline")
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
        ChatManager.sendMessage(p, false,"Invitation sent! (" + duration + "s)");

        if(!targetPlayer.isOnline()) return;

        String leaderName = p.getName();
        ChatManager.sendNotification(targetPlayer, "You received an invitation to join "+leaderName+"'s party! (/p accept "+leaderName+")");
    }

    @Subcommand("accept")
    @CommandCompletion("@PlayersOnline")
    public void accept(CommandSender sender, String leader){
        if(!(sender instanceof Player p)) return;

        UUID receiverUUID = p.getUniqueId();

        OfflinePlayer leaderPlayer = Bukkit.getPlayer(leader);

        if(leaderPlayer == null){
            ChatManager.sendMessage(p, true,"The party that you tried to join doesn't exist.");
            return;
        }

        UUID leaderUUID = leaderPlayer.getUniqueId();

        if(PartyManager.hasParty(receiverUUID)){
            ChatManager.sendMessage(p, true,"You're already in a party.");
            return;
        }

        String receiverName = p.getName();
        PartyInvite invite = PartyManager.getPendingInvite(receiverUUID,leaderUUID);

        if(invite == null || !PartyManager.hasPendingInvite(receiverUUID, leaderUUID)){
            ChatManager.sendMessage(p, true,"The invitation doesn't exist.");
            return;
        }

        SleepyParty party = invite.targetParty();

        if(party.getMembers().size() >= 10){ // I think I'm gonna erase this. But just for you to see.
            ChatManager.sendMessage(p, true,"The party is full!"); // Because I don't want to depend on the command
            return; // for the size of a party. I want that it depends of the dungeon that is being played.
        }

        if(System.currentTimeMillis() > invite.expirationTime()){
            PartyManager.removePendingInvite(receiverUUID, leaderUUID);
            ChatManager.sendMessage(p, true,"The invitation expired.");
            return;
        }

        for(UUID memberUUID : party.getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;

            ChatManager.sendMessage(memberPlayer, false,receiverName + " joined the party!");
        }

        String leaderName = leaderPlayer.getName();

        PartyManager.removePendingInvite(receiverUUID, leaderUUID);
        PartyManager.addToParty(receiverUUID,party);
        party.addMember(receiverUUID);

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

        party.disbandParty();
        PartyManager.removeParty(party);
        ChatManager.sendMessage(p, false,"Party disbanded!");
    }

    @Subcommand("kick")
    @CommandCompletion("@PlayersOnline")
    public void kick(CommandSender sender, String targetName){
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

        for(UUID memberUUID : party.getMembers()){
            OfflinePlayer offlineMember = Bukkit.getOfflinePlayer(memberUUID);
            String offlineName = offlineMember.getName();

            if(offlineName != null && offlineName.equalsIgnoreCase(targetName)){ // UUID found!
                targetUUID = memberUUID;
                break;
            }
        }

        if(targetUUID == null){
            ChatManager.sendMessage(leader, true,"The player wasn't found in the party.");
            return;
        }

        if(targetUUID.equals(leaderUUID)){
            ChatManager.sendMessage(leader, true,"You cannot kick yourself from your own party. Try (/p disband)");
            return;
        }

        // If we reached here, it means that the UUID is valid!
        party.removeMember(targetUUID);
        PartyManager.removeFromParty(targetUUID);

        Player targetPlayer = Bukkit.getPlayer(targetUUID);

        // We reutilize the 'target' var that we had previously declared.
        targetName = Bukkit.getOfflinePlayer(targetUUID).getName();

        for(UUID uuid : party.getMembers()){
            Player onlineMember = Bukkit.getPlayer(uuid);

            if(onlineMember == null) continue;

            ChatManager.sendNotification(onlineMember, targetName+"has been kicked from the party!");
        }

        if(targetPlayer != null && targetPlayer.isOnline()){
            ChatManager.sendMessage(targetPlayer, true,"You were kicked from your party! :(");
            }
        }

    @Subcommand("info|list")
    public void info(CommandSender sender){
        if(!(sender instanceof Player p)) return;
        UUID uuid = p.getUniqueId();

        if(!PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(p, true,"You're not currently in a party to see its info!");
            return;
        }

        SleepyParty party = PartyManager.getParty(uuid);
        StringBuilder builder = new StringBuilder();
        UUID leaderUUID = party.getLeader();

        OfflinePlayer leaderPlayer = Bukkit.getPlayer(leaderUUID);

        if(leaderPlayer == null) return;

        for(UUID memberUUID : party.getMembers()){
            OfflinePlayer memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || memberUUID == leaderUUID) continue;

            builder.append(memberPlayer.getName()).append(" ");
            }

        if(builder.isEmpty()) builder.append("(No members yet).");

        String finalMessage =
                (BasicPalette.DARK_GRAY.getColor() + "\n—\n" +
                        BasicPalette.YELLOW.getColor() + "Leader: " + BasicPalette.GRAY.getColor() + leaderPlayer.getName() + "\n" +
                        "Members: " + builder + "\n" +
                        BasicPalette.DARK_GRAY.getColor() + "—");

        ChatManager.sendMessage(p, false,finalMessage);
        }

    @Subcommand("leave")
    @CommandCompletion("@PlayersOnline")
    public void leave(CommandSender sender){
        if(!(sender instanceof Player leftPlayer)) return;
        UUID uuid = leftPlayer.getUniqueId();

        if(!PartyManager.hasParty(uuid)){
            ChatManager.sendMessage(leftPlayer, true,"You don't have a party. (/p create)");
            return;
        }

        String leftName = leftPlayer.getName();
        SleepyParty party = PartyManager.getParty(uuid);

        party.removeMember(uuid);
        PartyManager.removeParty(party);
        ChatManager.sendMessage(leftPlayer, false,"You left the party!");

        for(UUID memberUUID : party.getMembers()){
            Player memberPlayer = Bukkit.getPlayer(memberUUID);

            if(memberPlayer == null || !memberPlayer.isOnline()) continue;

            ChatManager.sendMessage(memberPlayer, false,leftName + " left the party!");
            }
        }
    }
