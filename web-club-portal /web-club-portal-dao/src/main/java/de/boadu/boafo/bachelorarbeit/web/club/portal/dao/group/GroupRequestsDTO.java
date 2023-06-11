package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(GroupRequestId.class)
public class GroupRequestsDTO implements MutableGroupRequest, GroupRequest {

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @Id
    private Long requesterId;

    @Id
    private Long groupId;

    private String name;

    private String surname;

    private Long adminId;

    private String groupName;

    @Override
    public int hashCode() {

        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.getRequesterId().hashCode();
        hash = ODD_PRIME_NUMBER * hash + this.getGroupId().hashCode();

        return hash;

    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof GroupRequestsDTO other){

            return ((this.getRequesterId() == other.getRequesterId())
                    && (this.getGroupId() == other.getGroupId()));


        }

        return false;
    }
}
