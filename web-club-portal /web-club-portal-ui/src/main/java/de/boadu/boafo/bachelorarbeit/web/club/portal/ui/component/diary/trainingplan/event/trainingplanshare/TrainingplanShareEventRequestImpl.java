package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import lombok.Data;

import java.util.Set;

@Data
public class TrainingplanShareEventRequestImpl implements TrainingPlanShareEventRequest {

    private final Set<Group> trainingGroups;

    @Override
    public Set<Group> getSelectedGroups() {
        return this.getTrainingGroups();
    }
}
