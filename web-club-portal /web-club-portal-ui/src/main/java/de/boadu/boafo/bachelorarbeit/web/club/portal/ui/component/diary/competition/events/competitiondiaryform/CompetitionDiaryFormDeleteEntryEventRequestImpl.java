package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform;

import lombok.Data;

@Data
public class CompetitionDiaryFormDeleteEntryEventRequestImpl implements CompetitionDiaryFormDeleteEntryEventRequest{

    private final Long clickedEntryId;

    @Override
    public Long getClickedEntryId() {
        return this.clickedEntryId;
    }
}
