package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform;

import lombok.Data;

@Data
public class TrainingsDiaryDeleteEntryEventRequestImpl implements TrainingsDiaryDeleteEntryEventRequest {

    private final Long clickedEntryId;

    @Override
    public Long getClickedEntryId() {
        return this.clickedEntryId;
    }
}
