package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import lombok.Data;

@Data
public class GroupDialogEventRequestImpl implements GroupDialogEventRequest {

    private final MutableGroup newGroup;

    @Override
    public MutableGroup getNewGroupToCreate() {
        return this.getNewGroup();
    }
}
