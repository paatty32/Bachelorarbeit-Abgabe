package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import lombok.Data;

@Data
public class AthleteDiaryGridEventRequestImpl implements AthleteDiaryGridEventRequest {

    private final AppUser appUser;

    @Override
    public AppUser getClickedPerson() {
        return this.getAppUser();
    }
}
