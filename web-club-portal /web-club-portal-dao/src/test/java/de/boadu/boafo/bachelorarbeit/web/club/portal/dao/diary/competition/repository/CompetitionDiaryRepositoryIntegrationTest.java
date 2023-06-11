package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Getter(AccessLevel.PRIVATE)
public class CompetitionDiaryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CompetitionDiaryRepository competitionDiaryRepository;

    private CompetitionDiaryDto competitionDiaryDto;

    @BeforeEach
    public void setUp(){

        DiaryId diaryId = DiaryId.builder()
                .diaryType(DiaryType.COMPETITION)
                .userId(39L)
                .build();

        this.competitionDiaryDto = new CompetitionDiaryDto(diaryId);

    }

    @Test
    public void whenSave_thenReturnSavedCompetitionDiary(){

        CompetitionDiaryDto savedCompetitionDiary = this.getCompetitionDiaryRepository().save(competitionDiaryDto);

        assertThat(savedCompetitionDiary).hasFieldOrPropertyWithValue("id", this.getCompetitionDiaryDto().getId());

    }

    @Test
    public void whenFindCompetitionDiary_thenReturnCompetitionDiary(){

        this.getTestEntityManager().persist(this.getCompetitionDiaryDto());

        CompetitionDiaryDto foundCompetitionDiary = this.getCompetitionDiaryRepository().findCompetitionDiaryById(this.getCompetitionDiaryDto().getId());

        assertThat(foundCompetitionDiary).isEqualTo(this.getCompetitionDiaryDto());
    }

    @Test
    public void whenNotFindCompetitionDiary_thenReturnNull(){



        DiaryId idToSearch = DiaryId.builder()
                .userId(334L)
                .diaryType(DiaryType.COMPETITION)
                .build();

        CompetitionDiaryDto notFoundDiary = this.getCompetitionDiaryRepository().findCompetitionDiaryById(idToSearch);

        assertThat(notFoundDiary).isNull();

    }

}
