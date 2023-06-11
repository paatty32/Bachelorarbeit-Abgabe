package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class CompetitionDiaryServiceImpl implements CompetitionDiaryService{

    private final CompetitionDiaryRepository competitionDiaryRepository;

    private final CompetitionDiaryEntryRepository competitionDiaryEntryRepository;

    @Override
    public CompetitionDiary getCompetitionDiaryByUser(DiaryId userId) {

        CompetitionDiaryDto competitionDiaryByUser = this.getCompetitionDiaryRepository().findCompetitionDiaryById(userId);

        return competitionDiaryByUser;
    }

    @Override
    public List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId) {

        List<CompetitionDiaryEntry> competitionDiaryEntries = new ArrayList<>();

        DiaryId competitionDiaryId = buildDiaryId(userId);

        CompetitionDiary competitionDiaryByUser = this.getCompetitionDiaryByUser(competitionDiaryId);
        List<CompetitionDiaryEntryDto> entry = competitionDiaryByUser.getEntry();

        if(entry != null) {

            competitionDiaryEntries.addAll(entry);

        }

        return competitionDiaryEntries;

    }

    @Override
    public void addNewCompetitionDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry) {

        DiaryId competitionDiaryId = buildDiaryId(userId);

        CompetitionDiary diary = this.getCompetitionDiaryByUser(competitionDiaryId);
        diary.getEntry().add((CompetitionDiaryEntryDto) newEntry);

        this.getCompetitionDiaryRepository().save((CompetitionDiaryDto) diary);
    }

    @Override
    public void deleteEntry(Long userId, Long entryId) {

        DiaryId competitionDiaryId = buildDiaryId(userId);

        CompetitionDiary diary = this.getCompetitionDiaryByUser(competitionDiaryId);

        CompetitionDiaryEntryDto entryById = this.getCompetitionDiaryEntryRepository().findEntryById(entryId);

        int entryIndex = diary.getEntry().indexOf(entryById);

        if(entryIndex != -1) {
            diary.getEntry().remove(entryIndex);
        }

        this.getCompetitionDiaryRepository().save((CompetitionDiaryDto) diary);

    }

    private DiaryId buildDiaryId(Long userId) {
        return DiaryId.builder()
                .userId(userId)
                .diaryType(DiaryType.COMPETITION)
                .build();
    }
}
