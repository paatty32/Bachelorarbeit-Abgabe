package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training;

import java.time.LocalDate;

public interface MutableTrainingDiaryEntry {

     void setId(Long id);

     void setDate(LocalDate date);

     void setSession(String session);

     void setFeeling(String session);


}
