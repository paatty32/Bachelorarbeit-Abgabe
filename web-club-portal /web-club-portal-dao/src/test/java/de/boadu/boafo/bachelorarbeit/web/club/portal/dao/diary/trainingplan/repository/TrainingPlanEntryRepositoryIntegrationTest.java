package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Getter(AccessLevel.PRIVATE)
public class TrainingPlanEntryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TrainingPlanEntryRepository trainingPlanEntryRepository;

    @Test
    public void whenTrainingPlanEntryIsSaved_thenReturnTrainingPlanEntry(){

        TrainingPlanEntryDTO trainingPlanEntry = new TrainingPlanEntryDTO();
        trainingPlanEntry.setId(1L);
        trainingPlanEntry.setSession("30x200m");
        trainingPlanEntry.setAthlete("Thomas");

        TrainingPlanEntryDTO savedEntry = this.getTrainingPlanEntryRepository().save(trainingPlanEntry);

        System.out.println(savedEntry.getId());
        assertThat(savedEntry).isEqualTo(trainingPlanEntry);

    }


}
