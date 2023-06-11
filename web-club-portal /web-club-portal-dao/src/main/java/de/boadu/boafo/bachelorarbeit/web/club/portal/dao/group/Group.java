package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;

import java.util.Set;

public interface Group {

    Long getId();
    String getName();

    String getTrainer();

    String getDescription();

    Long getAdminId();

    Set<GroupRequestsDTO> getRequests();

    Set<TrainingPlanEntryDTO> getTrainingPlanEntry();

     void removeEntry(TrainingPlanEntryDTO entry);

}
