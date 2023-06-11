package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;

import java.util.Set;

public interface TrainingDiaryShareDialogEventRequest {

    Set<AppUser> getTrainer();

    static TrainingDiaryShareDialogEventRequestImpl getInstanceOf(Set<AppUser> trainer){
        return new TrainingDiaryShareDialogEventRequestImpl(trainer);
    }
}
