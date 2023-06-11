package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform;

import lombok.Data;

@Data
public class TrainingPlanFormDeleteEntryEventRequestImpl implements TrainingPlanFormDeleteEntryEventRequest {

    private final Long entryId;

    @Override
    public Long getDeleteEntryId() {
        return this.getEntryId();
    }
}
