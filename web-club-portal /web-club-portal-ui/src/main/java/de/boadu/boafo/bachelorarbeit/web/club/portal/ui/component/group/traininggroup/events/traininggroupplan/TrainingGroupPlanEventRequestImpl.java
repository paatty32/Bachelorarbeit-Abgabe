package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan.TrainingGroupPlanEventRequest;
import lombok.Data;

@Data
public class TrainingGroupPlanEventRequestImpl implements TrainingGroupPlanEventRequest {

    private final TrainingPlanEntry trainingPlanEntry;

    @Override
    public TrainingPlanEntry getEntry() {
        return this.getTrainingPlanEntry();
    }
}
