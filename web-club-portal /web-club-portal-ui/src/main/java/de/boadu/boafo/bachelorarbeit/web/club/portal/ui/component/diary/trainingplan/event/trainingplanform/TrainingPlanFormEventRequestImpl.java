package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import lombok.Data;

@Data
public class TrainingPlanFormEventRequestImpl implements TrainingPlanFormEventRequest {

    private final MutableTrainingPlanEntry updaetedEntry;

    @Override
    public MutableTrainingPlanEntry getUpdatedEntry() {
        return this.updaetedEntry;
    }


}
