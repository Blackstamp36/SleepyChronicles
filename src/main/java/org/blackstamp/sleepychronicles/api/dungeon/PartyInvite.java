package org.blackstamp.sleepychronicles.api.dungeon;

public record PartyInvite(
        SleepyParty targetParty,
        Long expirationTime
){}
