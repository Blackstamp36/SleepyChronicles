package org.blackstamp.sleepychronicles.api.party;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SleepyParty {
    @Getter private final UUID leader;
    @Getter private final Set<UUID> members;

    public SleepyParty(UUID leader){
        this.leader = leader;
        this.members = new HashSet<>();
        this.members.add(leader);
    }

    public void addMember(UUID uuid){ this.getMembers().add(uuid); }
    public void removeMember(UUID uuid){ this.getMembers().remove(uuid); }
    public boolean hasMember(UUID uuid){ return this.getMembers().contains(uuid); }
    public void disbandParty(){
        for(UUID uuid : members){ removeMember(uuid); }
    }
}
