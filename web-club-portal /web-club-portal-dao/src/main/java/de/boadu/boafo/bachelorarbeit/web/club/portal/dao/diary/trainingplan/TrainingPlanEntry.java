package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupDTO;

import java.time.LocalDate;
import java.util.Set;

public interface TrainingPlanEntry {

    Long getId();

    String getSession();

    LocalDate getDate();

    String getAthlete();

    Set<GroupDTO> getGroups();

    void removeGroup(GroupDTO group);

}
