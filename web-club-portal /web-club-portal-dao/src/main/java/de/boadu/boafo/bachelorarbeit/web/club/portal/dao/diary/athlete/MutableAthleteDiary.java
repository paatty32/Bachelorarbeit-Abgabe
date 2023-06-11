package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;

import java.util.Set;

public interface MutableAthleteDiary {

    void setTrainerId(Long trainerId);

    void setAthleteId(Long athleteId);

    void setGroupId(Long groupId);

    void setAthleteEntries(Set<TrainingDiaryEntryDTO> entries);

}
