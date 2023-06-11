package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.Diary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingPlanDTO extends Diary implements TrainingPlan, MutableTrainingPlan {

    public TrainingPlanDTO(DiaryId id){

        super(id);

    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingPlanEntryDTO> entries;

}
