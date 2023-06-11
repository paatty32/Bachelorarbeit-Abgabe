package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GroupRequestId implements Serializable {

    private Long requesterId;

    private Long groupId;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof GroupRequestId other){

            return (this.getRequesterId() == other.getRequesterId()) &&
                    (this.getGroupId() == other.getGroupId());

        }

        return false;

    }

    @Override
    public int hashCode() {

        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.requesterId.hashCode();
        hash = ODD_PRIME_NUMBER * hash + this.groupId.hashCode();

        return hash;

    }
}
