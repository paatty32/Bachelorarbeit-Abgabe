package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform;

public interface CompetitionDiaryFormEventListener {

    void handleButtonDelete(CompetitionDiaryFormDeleteEntryEventRequest event);

    void handleButtonUpdate(CompetitionDiaryFormEventRequest event);
}
