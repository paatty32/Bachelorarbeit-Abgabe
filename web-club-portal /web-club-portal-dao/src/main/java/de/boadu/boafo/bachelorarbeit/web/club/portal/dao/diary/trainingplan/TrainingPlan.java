package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;

import java.util.List;

public interface TrainingPlan {

    List<TrainingPlanEntryDTO> getEntries();

}
