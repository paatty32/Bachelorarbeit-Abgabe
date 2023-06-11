package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;

public interface TrainingPlanEntryUiService {
    TrainingPlanEntry getEntry(Long deleteEntryId);
}
