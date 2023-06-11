package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionDiaryEntryRepository extends JpaRepository<CompetitionDiaryEntryDto, Long> {

    CompetitionDiaryEntryDto save(CompetitionDiaryEntryDto entry);

    CompetitionDiaryEntryDto findEntryById(Long id);

}
