package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform;

public interface TrainingsDairyFormEventListener {

    void handleButtonUpdate(TrainingsDiaryFormEventRequest event);

    void handleButtonDelete(TrainingsDiaryDeleteEntryEventRequest event);

    void handleButtonShare();
}
