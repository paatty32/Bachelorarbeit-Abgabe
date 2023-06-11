package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan;

import java.time.LocalDate;

public interface MutableTrainingPlanEntry {

    void setId(Long id);

    void setSession(String session);

    void setDate(LocalDate date);

    void setAthlete(String athlete);

}
