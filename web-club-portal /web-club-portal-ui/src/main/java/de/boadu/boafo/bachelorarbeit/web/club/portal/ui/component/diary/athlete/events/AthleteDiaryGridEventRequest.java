package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;

public interface AthleteDiaryGridEventRequest {

    AppUser getClickedPerson();

    static AthleteDiaryGridEventRequestImpl getInstance(AppUser appUser){

        return new AthleteDiaryGridEventRequestImpl(appUser);

    }
}
