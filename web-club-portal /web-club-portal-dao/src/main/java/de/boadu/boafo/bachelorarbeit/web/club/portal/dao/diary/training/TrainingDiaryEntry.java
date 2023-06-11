package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;

import java.time.LocalDate;
import java.util.Set;

public interface TrainingDiaryEntry {

     Long getId();
     LocalDate getDate();
     String getSession();
     String getFeeling();
     Boolean getIsShared();
     Set<AthleteDiaryDto> getAthleteDiaries();


}
