package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import lombok.Data;

@Data
public class CompetitionDiaryFormEventRequestImpl implements CompetitionDiaryFormEventRequest {

    private final MutableCompetitionDiaryEntry entry;

    @Override
    public MutableCompetitionDiaryEntry getClickedEntry() {
        return this.getEntry();
    }
}
