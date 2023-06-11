package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Getter
public class CompetitionDiaryServiceImplTest {

    @Mock
    private CompetitionDiaryRepository competitionDiaryRepository;

    @Mock
    private CompetitionDiaryEntryRepository competitionDiaryEntryRepository;

    @Mock
    CompetitionDiary competitionDiary;

    @InjectMocks
    private CompetitionDiaryServiceImpl competitionDiaryService;



    private AppUserDTO max;

    @BeforeEach
    public void setUp(){

        this.max = AppUserDTO.builder()
                .surname("Mustermann")
                .name("Max")
                .id(1L)
                .build();

    }

    @Test
    public void whenCompetitionDiaryByUserIsThere_thenReturnCompetitionDiary(){

        DiaryId maxCompDiaryId = DiaryId.builder()
                .userId(max.getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        CompetitionDiaryDto compDiary = new CompetitionDiaryDto(maxCompDiaryId);

        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(maxCompDiaryId)).thenReturn(compDiary);

        CompetitionDiary foundCompetitionDiary = this.competitionDiaryService.getCompetitionDiaryByUser(maxCompDiaryId);

        assertThat(foundCompetitionDiary).hasFieldOrPropertyWithValue("id", compDiary.getId());

    }

    @Test
    public void whenCompetitionDiaryByUserIsNotThere_thenReturnNull(){

        DiaryId competitionDiaryIdByUser = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        CompetitionDiary notFoundDiary = this.competitionDiaryService.getCompetitionDiaryByUser(competitionDiaryIdByUser);

        assertThat(notFoundDiary).isNull();

    }

    @Test
    public void whenCompetitionDiaryEntriesByUserIsThere_thenReturnEntries(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        CompetitionDiaryEntryDto compEntry = CompetitionDiaryEntryDto.builder()
                .id(1L)
                .place("Düsseldorf")
                .dicipline("400m")
                .build();

        List<CompetitionDiaryEntryDto> compEntries = new ArrayList<>();

        CompetitionDiaryDto compDiary = new CompetitionDiaryDto(diaryId);
        compDiary.setEntry(compEntries);
        compDiary.getEntry().add(compEntry);

        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(diaryId)).thenReturn(compDiary);

        List<CompetitionDiaryEntry> competitionDiaryEntriesByUser = this.getCompetitionDiaryService().getCompetitionDiaryEntriesByUser(1L);

        assertThat(competitionDiaryEntriesByUser.size()).isEqualTo(compDiary.getEntry().size());

    }

    @Test
    public void whenCompetitionDiaryEntriesByUserIsNotThere_thenReturZero(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        CompetitionDiaryDto noEntryDiary = new CompetitionDiaryDto(diaryId);

        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(diaryId)).thenReturn(noEntryDiary);

        List<CompetitionDiaryEntry> competitionDiaryEntriesByUser = this.getCompetitionDiaryService().getCompetitionDiaryEntriesByUser(this.getMax().getId());

        assertThat(competitionDiaryEntriesByUser.size()).isEqualTo(0);

    }

    @Test
    public void whenAddNewCompetitionDiaryEntry_thenReturnEntry(){

        MutableCompetitionDiaryEntry newEntry = new CompetitionDiaryEntryDto();
        newEntry.setPlace("Düsseldorf");
        newEntry.setDicipline("400m");
        newEntry.setResult("40 sek.");

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        List<CompetitionDiaryEntryDto> compEntries = new ArrayList<>();

        CompetitionDiaryDto competitionDiary = new CompetitionDiaryDto(diaryId);
        competitionDiary.setEntry(compEntries);

        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(diaryId)).thenReturn(competitionDiary);

        this.getCompetitionDiaryService().addNewCompetitionDiaryEntry(this.getMax().getId(), newEntry);

        assertThat(competitionDiary.getEntry().get(0).getPlace()).isEqualTo("Düsseldorf");
        assertThat(competitionDiary.getEntry().get(0).getDicipline()).isEqualTo("400m");
        assertThat(competitionDiary.getEntry().get(0).getResult()).isEqualTo("40 sek.");

    }

    @Test
    public void whenCompetitionDiaryEntryIsDeleted_thenReturnEmptyEntries(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        List<CompetitionDiaryEntryDto> compEntries = new ArrayList<>();

        CompetitionDiaryDto competitionDiary = new CompetitionDiaryDto(diaryId);
        competitionDiary.setEntry(compEntries);

        CompetitionDiaryEntryDto compEntry = new CompetitionDiaryEntryDto();
        compEntry.setId(3L);
        compEntry.setPlace("Düsseldorf");
        compEntry.setDicipline("400m");
        compEntry.setResult("40 sek.");

        competitionDiary.getEntry().add(compEntry);

        lenient().when(this.getCompetitionDiaryEntryRepository().findEntryById(3L)).thenReturn(compEntry);
        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(diaryId)).thenReturn(competitionDiary);

        this.getCompetitionDiaryService().deleteEntry(this.getMax().getId(), 3L);

        assertThat(competitionDiary.getEntry().size()).isEqualTo(0);

    }

    @Test
    public void whenCompetitionDiaryToDeleteIsNotThere_thenDeleteNothing(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getMax().getId())
                .diaryType(DiaryType.COMPETITION)
                .build();

        List<CompetitionDiaryEntryDto> compEntries = new ArrayList<>();

        CompetitionDiaryDto competitionDiary = new CompetitionDiaryDto(diaryId);
        competitionDiary.setEntry(compEntries);

        CompetitionDiaryEntryDto compEntry = new CompetitionDiaryEntryDto();
        compEntry.setId(3L);
        compEntry.setPlace("Düsseldorf");
        compEntry.setDicipline("400m");
        compEntry.setResult("40 sek.");

        competitionDiary.getEntry().add(compEntry);

        lenient().when(this.getCompetitionDiaryEntryRepository().findEntryById(3L)).thenReturn(compEntry);
        Mockito.when(this.getCompetitionDiaryRepository().findCompetitionDiaryById(diaryId)).thenReturn(competitionDiary);

        this.getCompetitionDiaryService().deleteEntry(this.getMax().getId(), 5L);

        assertThat(competitionDiary.getEntry().size()).isEqualTo(1);

    }


}
