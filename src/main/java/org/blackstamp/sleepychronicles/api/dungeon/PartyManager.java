package org.blackstamp.sleepychronicles.api.dungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyManager {
    private static final Map<UUID, SleepyParty> ACTIVE_PARTIES = new HashMap<>();
    private static final Map<UUID, Map<UUID, PartyInvite>> PENDING_INVITES = new HashMap<>();

    public static void addToParty(UUID uuid,SleepyParty party){ ACTIVE_PARTIES.put(uuid,party); }
    public static SleepyParty getParty(UUID uuid){ return ACTIVE_PARTIES.get(uuid); }
    public static void removeFromParty(UUID uuid){ ACTIVE_PARTIES.remove(uuid); }
    public static boolean hasParty(UUID uuid){ return ACTIVE_PARTIES.containsKey(uuid); }

    public static void addPendingInvite(UUID receiver, UUID leader, PartyInvite invite){
        PENDING_INVITES.computeIfAbsent(receiver, k -> new HashMap<>())
                .put(leader,invite);
    }

    public static PartyInvite getPendingInvite(UUID receiver, UUID leader){
        Map<UUID,PartyInvite> receiverInvites = PENDING_INVITES.get(receiver);

        if(receiverInvites == null) return null;

        return receiverInvites.get(leader);
    }

    public static void removePendingInvite(UUID receiver, UUID leader){
        Map<UUID,PartyInvite> receiverInvites = PENDING_INVITES.get(receiver);

        if(receiverInvites == null) return;

        receiverInvites.remove(leader);

        if(receiverInvites.isEmpty()){ PENDING_INVITES.remove(receiver); }
    }

    public static boolean hasPendingInvite(UUID receiver, UUID leader){
        Map<UUID,PartyInvite> receiverInvites = PENDING_INVITES.get(receiver);

        if(receiverInvites == null) return false;

        return receiverInvites.containsKey(leader);
    }
}
