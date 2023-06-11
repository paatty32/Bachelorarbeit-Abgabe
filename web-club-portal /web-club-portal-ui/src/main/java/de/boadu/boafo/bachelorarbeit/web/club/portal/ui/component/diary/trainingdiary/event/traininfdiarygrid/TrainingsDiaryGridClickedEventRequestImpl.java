package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import lombok.Data;

@Data
public class TrainingsDiaryGridClickedEventRequestImpl implements TrainingsDiaryGridClickedEventRequest {

    private final TrainingDiaryEntry trainingDiaryEntry;

    @Override
    public TrainingDiaryEntry getEntry() {
        return this.getTrainingDiaryEntry();
    }
}
