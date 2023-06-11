package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.*;

import java.util.List;

public interface CompetitionDiaryService {

    CompetitionDiary getCompetitionDiaryByUser(DiaryId userId);

    List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId);

    void addNewCompetitionDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry);

    void deleteEntry(Long userId, Long entry);

}