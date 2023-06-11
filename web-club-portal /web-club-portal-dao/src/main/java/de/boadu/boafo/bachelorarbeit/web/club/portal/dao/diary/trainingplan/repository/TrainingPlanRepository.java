package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlanDTO, DiaryId> {

    TrainingPlanDTO save(TrainingPlanDTO trainingPlan);

    TrainingPlanDTO findTrainingPlanById(DiaryId id);
}
