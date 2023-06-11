package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplan;


import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;

import java.util.List;

public interface TrainingPlanService {
    void addNewTrainingPlanEntry(Long userId, MutableTrainingPlanEntry newEntry);

    List<TrainingPlanEntry> getTrainingPlanEntries(Long userId);

    void deleteTrainingPlanEntry(Long userId, Long deleteEntryId);
}
