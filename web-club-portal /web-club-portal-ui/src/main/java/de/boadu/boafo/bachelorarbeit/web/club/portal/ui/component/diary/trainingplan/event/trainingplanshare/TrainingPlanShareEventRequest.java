package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;

import java.util.Set;

public interface TrainingPlanShareEventRequest {

    Set<Group> getSelectedGroups();

    static TrainingPlanShareEventRequest getInstance(Set<Group> selectedGroups){
        return new TrainingplanShareEventRequestImpl(selectedGroups);
    }

}
