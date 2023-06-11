package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository;


import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Getter(AccessLevel.PRIVATE)
public class TrainingDiaryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TrainingsDiaryRepository trainingsDiaryRepository;

    @Test
    public void whenSave_thenReturnSavedTrainingDiary(){

        DiaryId diaryId = DiaryId.builder().diaryType(DiaryType.TRAINING)
                .userId(1L).build();

        TrainingDiaryDto trainingDiaryDto = new TrainingDiaryDto(diaryId);

        TrainingDiaryDto savedTrainingDiary = this.getTrainingsDiaryRepository().save(trainingDiaryDto);

        assertThat(savedTrainingDiary).hasFieldOrPropertyWithValue("id", diaryId);
    }

    @Test
    public void whenFindTrainingDiary_thenReturnTrainingDiary(){

        DiaryId diaryId = DiaryId.builder().diaryType(DiaryType.TRAINING)
                .userId(1L).build();

        TrainingDiaryDto trainingDiaryDto = new TrainingDiaryDto(diaryId);

        this.getTestEntityManager().persist(trainingDiaryDto);

        TrainingDiaryDto foundTrainingDiaryById = this.getTrainingsDiaryRepository().findTrainingDiaryById(diaryId);

        assertThat(foundTrainingDiaryById).isEqualTo(trainingDiaryDto);

    }

    @Test
    public void whenNotFindTrainingDiary_thenReturnNull(){

        DiaryId diaryIdtoSearch = DiaryId.builder().diaryType(DiaryType.TRAINING)
                .userId(2L).build();

        DiaryId diaryId = DiaryId.builder().diaryType(DiaryType.TRAINING)
                .userId(1L).build();

        TrainingDiaryDto trainingDiaryDto = new TrainingDiaryDto(diaryId);

        this.getTestEntityManager().persist(trainingDiaryDto);

        TrainingDiaryDto notFoundTrainingDiaryById = this.getTrainingsDiaryRepository().findTrainingDiaryById(diaryIdtoSearch);

        assertThat(notFoundTrainingDiaryById).isNull();

    }

}
