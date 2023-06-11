package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Getter(AccessLevel.PRIVATE)
public class TrainingPlanRepositoryIntegrationTest {

    @Autowired
    TrainingPlanRepository trainingPlanRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private DiaryId diaryId;

    @BeforeEach
    public void setUp(){

        diaryId = DiaryId.builder().userId(2L)
                .diaryType(DiaryType.TRAININGPLAN)
                .build();

    }

    @Test
    public void whenTrainingPlanIsSaved_thenReturnTrainingPlan(){

        TrainingPlanDTO trainingPlanDTO = new TrainingPlanDTO(this.getDiaryId());

        TrainingPlanDTO savedTrainingPlan = this.getTrainingPlanRepository().save(trainingPlanDTO);

        assertThat(savedTrainingPlan).hasFieldOrPropertyWithValue("id", this.getDiaryId());

    }

    @Test
    public void whenFindTrainingPlan_thenReturnTrainingPlan(){

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());

        this.getTestEntityManager().persist(trainingPlan);

        TrainingPlanDTO trainingPlanById = this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId());

        assertThat(trainingPlanById).isEqualTo(trainingPlan);


    }

    @Test
    public void whenNotFindTrainingPlan_thenReturnNull(){

        DiaryId diaryId2 = DiaryId.builder().diaryType(DiaryType.TRAININGPLAN)
                .userId(3L).build();

        TrainingPlanDTO trainingPlanById = this.getTrainingPlanRepository().findTrainingPlanById(diaryId2);

        assertThat(trainingPlanById).isNull();
    }

}
