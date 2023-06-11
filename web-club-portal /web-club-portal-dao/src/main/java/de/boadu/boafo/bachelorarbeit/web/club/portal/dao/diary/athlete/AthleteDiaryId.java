package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AthleteDiaryId implements Serializable {

    private Long trainerId;

    private Long athleteId;

    private Long groupId;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @Override
    public int hashCode() {

        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.trainerId.hashCode();
        hash = ODD_PRIME_NUMBER * hash + this.groupId.hashCode();
        hash = ODD_PRIME_NUMBER * hash + this.athleteId.hashCode();

        return hash;


    }


    @Override
    public boolean equals(Object obj) {

        if(obj instanceof AthleteDiaryId other){

            return (this.getTrainerId() == other.getTrainerId()) &&
                    (this.getAthleteId() == other.getAthleteId()) &&
                    (this.getGroupId() == other.getGroupId());

        }

        return false;

    }

}
