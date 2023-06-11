package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiaryentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository.AppUserRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiary.CompetitionDiaryService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompetitionDiaryEntryServiceImpl implements CompetitionDiaryEntryService{

    private final CompetitionDiaryEntryRepository competitionDiaryEntryRepository;

    private final AppUserRepository appUserRepository;

    private final CompetitionDiaryService competitionDiaryService;

    private final CompetitionDiaryRepository competitionDiaryRepository;


    @Override
    public CompetitionDiaryEntryDto updateCompetitionEntry(MutableCompetitionDiaryEntry entry) {

        CompetitionDiaryEntryDto updatedEntry = (CompetitionDiaryEntryDto) entry;

        CompetitionDiaryEntryDto updatedCompEntry = this.getCompetitionDiaryEntryRepository().save(updatedEntry);

        return updatedCompEntry;

    }

}
