package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Getter(AccessLevel.PRIVATE)
public class CompetitionDiaryEntryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CompetitionDiaryEntryRepository competitionDiaryEntryRepository;

    @Test
    public void whenSave_thenReturnCompetitionEntry(){

        CompetitionDiaryEntryDto competitionDiaryEntry = CompetitionDiaryEntryDto.builder()
                .id(1L)
                .place("Düsseldorf")
                .date(LocalDate.parse("01.11.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .dicipline("100m")
                .result("9,58 sek.")
                .feeling("super")
                .build();

        CompetitionDiaryEntryDto savedCompetitionEntry = this.getCompetitionDiaryEntryRepository().save(competitionDiaryEntry);

        assertThat(savedCompetitionEntry).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedCompetitionEntry).hasFieldOrPropertyWithValue("place", "Düsseldorf");
        assertThat(savedCompetitionEntry).hasFieldOrPropertyWithValue("date", LocalDate.parse("01.11.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertThat(savedCompetitionEntry).hasFieldOrPropertyWithValue("dicipline", "100m");
        assertThat(competitionDiaryEntry).hasFieldOrPropertyWithValue("result", "9,58 sek.");
        assertThat(competitionDiaryEntry).hasFieldOrPropertyWithValue("feeling", "super");

    }
}
