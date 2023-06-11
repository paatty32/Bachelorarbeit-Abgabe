package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition;

import java.time.LocalDate;

public interface MutableCompetitionDiaryEntry {

    void setId(Long id);

    void setDate(LocalDate date);

    void setPlace(String place);

    void setDicipline(String dicipline);

    void setResult(String resaults);

    void setFeeling(String feeling);
}
