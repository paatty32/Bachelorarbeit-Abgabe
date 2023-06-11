package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import lombok.Data;

import java.util.Set;

@Data
public class TrainingDiaryShareDialogEventRequestImpl implements TrainingDiaryShareDialogEventRequest{

    private final Set<AppUser> clickedTrainer;

    @Override
    public Set<AppUser> getTrainer() {
        return this.getClickedTrainer();
    }
}
