package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;

import java.util.List;

public interface TrainingPlanUiService {

    List<TrainingPlanEntry> getTrainingPlanEntriesByUser(Long userId);

    void addnewTrainingPlanEntry(Long id, MutableTrainingPlanEntry newEntry);

    void updateEntry(MutableTrainingPlanEntry entry);

    void deleteTrainingPlanEntry(Long userId, Long deleteEntryId);
}
