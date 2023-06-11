package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplanentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;

public interface TrainingPlanEntryService {

    void updateEntry(MutableTrainingPlanEntry entry);

    TrainingPlanEntry getTrainingPlanEntry(Long id);

}
