package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;

import java.util.Set;

public interface AthleteDiary {

    Long getTrainerId();

    Long getAthleteId();

    Long getGroupId();

    Set<TrainingDiaryEntryDTO> getAthleteEntries();

     void removeEntry(TrainingDiaryEntryDTO entry);

}
