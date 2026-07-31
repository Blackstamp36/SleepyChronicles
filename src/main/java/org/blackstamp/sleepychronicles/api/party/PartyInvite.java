package org.blackstamp.sleepychronicles.api.party;

public record PartyInvite(
        SleepyParty targetParty,
        Long expirationTime
){}
