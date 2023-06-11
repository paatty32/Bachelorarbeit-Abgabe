package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;

import java.util.List;

public interface CompetitionDiaryUiService {

    void addNewDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry);

    List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId);

    CompetitionDiaryEntryDto upadeEntry(MutableCompetitionDiaryEntry updatedEntry);

    void deleteEntry(Long userId, Long deleteEntry);
}
