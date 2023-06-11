package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.Diary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompetitionDiaryDto extends Diary implements CompetitionDiary, MutableCompetitionDiaryDto {

    public CompetitionDiaryDto(DiaryId id){
        super(id);
    }


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetitionDiaryEntryDto> entry;

}
