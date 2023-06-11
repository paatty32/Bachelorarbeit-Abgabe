package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid;

import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridClickedEventRequest;

public interface TrainingsDiaryGridEventListener {
    void handleClickGrid(TrainingsDiaryGridClickedEventRequest event);

    void handleClickAdd();

}
