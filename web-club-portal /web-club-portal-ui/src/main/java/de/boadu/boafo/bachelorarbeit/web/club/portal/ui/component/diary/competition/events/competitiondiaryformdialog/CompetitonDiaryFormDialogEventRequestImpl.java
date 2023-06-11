package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import lombok.Data;

@Data
public class CompetitonDiaryFormDialogEventRequestImpl implements CompetitonDiaryFormDialogEventRequest {

   private final MutableCompetitionDiaryEntry competitionDiaryEntry;

   @Override
    public MutableCompetitionDiaryEntry getEntry(){

       return this.getCompetitionDiaryEntry();

   }

}
