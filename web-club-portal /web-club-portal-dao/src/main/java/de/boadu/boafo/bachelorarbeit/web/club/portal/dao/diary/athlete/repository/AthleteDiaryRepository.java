package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteDiaryRepository extends JpaRepository<AthleteDiaryDto, DiaryId> {

    AthleteDiaryDto save(AthleteDiaryDto diary);

    @EntityGraph(attributePaths = "athleteEntries")
    List<AthleteDiaryDto> getDiaryByTrainerId(Long trainerId);

    @EntityGraph(attributePaths = "athleteEntries")
    AthleteDiaryDto getDiaryByTrainerIdAndAthleteId(Long trainerId, Long athleteId);

    /*@EntityGraph(attributePaths = "athleteEntries")
    List<AthleteDiaryDto> getDiaryById(Long diaryId);

     */

}
