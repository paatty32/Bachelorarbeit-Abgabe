package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;

import java.util.Set;

public interface MutableGroup {

    void setId(Long id);

    void setName(String name);

    void setTrainer(String trainerName);

    void setDescription(String description);

    void setAdminId(Long id);

    void setTrainingPlanEntry(Set<TrainingPlanEntryDTO> entries);

}
