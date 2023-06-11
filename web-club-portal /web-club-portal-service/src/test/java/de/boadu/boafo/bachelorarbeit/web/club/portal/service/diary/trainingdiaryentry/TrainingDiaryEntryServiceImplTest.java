package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingdiaryentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingdiaryentry.TrainingDiaryEntryServiceImpl;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Getter
public class TrainingDiaryEntryServiceImplTest {

    @Mock
    private TestEntityManager testEntityManager;

    @Mock
    TrainingsDiaryEntryRepository trainingsDiaryEntryRepository;

    @InjectMocks
    TrainingDiaryEntryServiceImpl trainingDiaryEntryService;

    @Test
    public void whenTrainingEntryIsUpdated_thenRertunUpdatedEntry(){

        TrainingDiaryEntryDTO diaryEntry = TrainingDiaryEntryDTO.builder()
                .id(1L)
                .session("10 x 30m")
                .feeling("Gut")
                .build();

        TrainingDiaryEntryDTO updatedEntry = TrainingDiaryEntryDTO.builder()
                .id(1L)
                .feeling("nicht so Gut.")
                .build();

        this.getTestEntityManager().persist(diaryEntry);

        Mockito.when(this.getTrainingsDiaryEntryRepository().save(diaryEntry)).thenReturn(updatedEntry);

        TrainingDiaryEntryDTO updateEntry = this.getTrainingDiaryEntryService().updateEntry(updatedEntry);

        assertThat(updateEntry).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedEntry).hasFieldOrPropertyWithValue("feeling", "nicht so Gut.");

    }



}
