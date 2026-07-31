package org.blackstamp.sleepychronicles.api.party;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SleepyParty {
    @Getter private final UUID leader;
    @Getter private final Set<UUID> members;

    private final int MAX_SIZE = 4;

    public SleepyParty(UUID leader){
        this.leader = leader;
        this.members = new HashSet<>();
        this.members.add(leader);
    }

    public void addMember(UUID uuid){
        if(this.getMembers().size() < MAX_SIZE){ this.getMembers().add(uuid); }
    }
    public void removeMember(UUID uuid){ this.getMembers().remove(uuid); }
}
