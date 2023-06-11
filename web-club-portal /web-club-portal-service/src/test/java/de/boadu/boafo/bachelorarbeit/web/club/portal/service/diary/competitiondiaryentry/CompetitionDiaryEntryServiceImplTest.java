package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiaryentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryEntryRepository;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@Getter
public class CompetitionDiaryEntryServiceImplTest {

    @Mock
    CompetitionDiaryEntryRepository competitionDiaryEntryRepository;

    @InjectMocks
    CompetitionDiaryEntryServiceImpl competitionDiaryEntryService;


    private CompetitionDiaryEntryDto testEntry;

    @BeforeEach
    public void setUp(){

        this.testEntry = CompetitionDiaryEntryDto.builder()
                .date(LocalDate.parse("11.11.2011", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .id(1L)
                .place("Düsseldorf")
                .dicipline("200m Sprint")
                .result("19,34 sek.")
                .feeling("Super")
                .build();
    }

    @Test
    public void whenCompetitionEntryIsUpdated_thenReturnUpdatedEntry(){

        Mockito.when(this.getCompetitionDiaryEntryRepository().save(this.getTestEntry())).thenReturn(this.getTestEntry());

        this.getTestEntry().setPlace("Köln");

        CompetitionDiaryEntryDto updatedEntry = this.getCompetitionDiaryEntryService().updateCompetitionEntry(this.getTestEntry());

        assertThat(updatedEntry).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("place", "Köln");
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("date", LocalDate.parse("11.11.2011", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("dicipline", "200m Sprint");
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("result", "19,34 sek.");
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("feeling", "Super");

    }

}
