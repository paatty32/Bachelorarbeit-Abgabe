package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingsDiaryRepository extends JpaRepository<TrainingDiaryDto, DiaryId> {

    TrainingDiaryDto findTrainingDiaryById(DiaryId id);

    TrainingDiaryDto save(TrainingDiaryDto trainingDiary);

}
