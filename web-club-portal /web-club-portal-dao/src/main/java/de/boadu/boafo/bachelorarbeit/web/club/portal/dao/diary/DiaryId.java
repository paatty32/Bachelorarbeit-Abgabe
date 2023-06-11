package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DiaryId implements Serializable {

    private Long userId;

    private DiaryType diaryType;

    final int SEED =23; //random Zahl
    final int ODD_PRIME_NUMBER = 37;

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof DiaryId otherDiaryId){

            return (this.userId == otherDiaryId.userId) && (this.diaryType == otherDiaryId.diaryType);

        }

        return false;
    }

    @Override
    public int hashCode(){
        int hash = SEED;
        hash = ODD_PRIME_NUMBER * hash + this.userId.hashCode();
        hash = ODD_PRIME_NUMBER * hash + this.diaryType.hashCode();

        return hash;
    }
}
