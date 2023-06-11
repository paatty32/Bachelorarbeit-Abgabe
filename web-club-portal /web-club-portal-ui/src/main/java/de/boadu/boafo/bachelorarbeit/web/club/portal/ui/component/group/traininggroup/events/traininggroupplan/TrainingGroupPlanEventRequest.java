package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;

public interface TrainingGroupPlanEventRequest {

     static TrainingGroupPlanEventRequestImpl instanceOf(TrainingPlanEntry entry){

        return new TrainingGroupPlanEventRequestImpl(entry);

    }

    TrainingPlanEntry getEntry();
}
