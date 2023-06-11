package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TrainingsDiaryEntryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TrainingsDiaryEntryRepository trainingsDiaryEntryRepository;

    @Test
    public void whenSave_thenReturnTrainingsDairyEntry(){

        TrainingDiaryEntryDTO testEntry = TrainingDiaryEntryDTO.builder()
                .id(1L)
                .date(LocalDate.parse("02.11.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .session("10x1000")
                .feeling("foo")
                .isShared(false)
                .build();

        TrainingDiaryEntryDTO savedTestEntry = this.trainingsDiaryEntryRepository.save(testEntry);

        assertThat(savedTestEntry).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedTestEntry).hasFieldOrPropertyWithValue("date", LocalDate.parse("02.11.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertThat(savedTestEntry).hasFieldOrPropertyWithValue("session", "10x1000");
        assertThat(savedTestEntry).hasFieldOrPropertyWithValue("feeling", "foo");
        assertThat(savedTestEntry).hasFieldOrPropertyWithValue("isShared", false);
    }
}
