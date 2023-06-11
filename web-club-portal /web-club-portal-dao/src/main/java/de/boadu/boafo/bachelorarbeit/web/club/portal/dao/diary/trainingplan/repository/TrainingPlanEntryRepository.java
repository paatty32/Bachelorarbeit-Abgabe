package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanEntryRepository extends JpaRepository<TrainingPlanEntryDTO, Long> {

    TrainingPlanEntryDTO save(TrainingPlanEntryDTO entry);

    @EntityGraph(attributePaths = "groups")
    TrainingPlanEntryDTO findEntryById(Long id);

    void deleteEntryById(Long id);

}
