package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform;

public interface TrainingPlanFormDeleteEntryEventRequest {

    Long getDeleteEntryId();

    static TrainingPlanFormDeleteEntryEventRequest getInstance(Long entryId){

        return new TrainingPlanFormDeleteEntryEventRequestImpl(entryId);

    }

}
