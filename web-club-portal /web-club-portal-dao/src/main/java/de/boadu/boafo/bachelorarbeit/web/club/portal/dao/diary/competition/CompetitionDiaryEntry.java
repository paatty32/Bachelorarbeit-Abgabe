package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition;

import java.time.LocalDate;

public interface CompetitionDiaryEntry {

    public Long getId();

    public LocalDate getDate();

    public String getPlace();

    public String getDicipline();

    public String getResult();

    public String getFeeling();
}
