package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionDiaryRepository extends JpaRepository<CompetitionDiaryDto, DiaryId> {

    CompetitionDiaryDto findCompetitionDiaryById(DiaryId id);

    CompetitionDiaryDto save(CompetitionDiaryDto competitionDiary);
}
