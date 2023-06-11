package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform;


public interface TrainingPlanFormEventListener {

    void handleButtonUpdate(TrainingPlanFormEventRequest event);

    void handleButtonDelete(TrainingPlanFormDeleteEntryEventRequest event);

    void handleButtonShare();


}
