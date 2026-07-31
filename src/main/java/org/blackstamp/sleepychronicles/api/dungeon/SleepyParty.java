package org.blackstamp.sleepychronicles.api.dungeon;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SleepyParty {
    @Getter private final UUID leader;
    @Getter private final Set<UUID> members;
    @Getter private final int floor;

    private final int MAX_SIZE = 4;

    public SleepyParty(UUID leader, int floor){
        this.leader = leader;
        this.floor = floor;
        this.members = new HashSet<>();
        this.members.add(leader);
    }

    public void addMember(UUID uuid){
        if(this.getMembers().size() < MAX_SIZE) this.getMembers().add(uuid);
    }
    public void removeMember(UUID uuid){ this.getMembers().remove(uuid); }
}
