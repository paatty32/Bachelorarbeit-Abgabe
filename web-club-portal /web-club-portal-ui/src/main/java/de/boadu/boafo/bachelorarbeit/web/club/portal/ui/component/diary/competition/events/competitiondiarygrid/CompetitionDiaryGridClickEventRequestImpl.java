package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import lombok.Data;

@Data
public class CompetitionDiaryGridClickEventRequestImpl implements CompetitionDiaryGridClickEventRequest {

    private final CompetitionDiaryEntry entry;

    @Override
    public CompetitionDiaryEntry getClickedEntry() {
        return this.getEntry();
    }
}
