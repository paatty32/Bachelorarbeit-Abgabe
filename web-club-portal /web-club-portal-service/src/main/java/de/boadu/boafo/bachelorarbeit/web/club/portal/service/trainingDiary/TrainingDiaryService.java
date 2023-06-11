package de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingDiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;

import java.util.List;

public interface TrainingDiaryService {
    TrainingDiary getTrainingsDiaryByUser(DiaryId userId);

     void addNewTrainingDiaryEntry(long userId, TrainingDiaryEntry newEntry);

    List<TrainingDiaryEntry> getTrainingsDiaryEntriesByUser(Long userId);

    void deleteEntry(Long currentPersonId, Long selectedEntry);



}
