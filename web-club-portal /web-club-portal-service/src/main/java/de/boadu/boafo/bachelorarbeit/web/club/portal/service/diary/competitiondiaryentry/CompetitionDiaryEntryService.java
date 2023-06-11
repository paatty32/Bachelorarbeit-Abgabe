package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiaryentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;

public interface CompetitionDiaryEntryService {

    CompetitionDiaryEntryDto updateCompetitionEntry(MutableCompetitionDiaryEntry updatedEntry);

}
