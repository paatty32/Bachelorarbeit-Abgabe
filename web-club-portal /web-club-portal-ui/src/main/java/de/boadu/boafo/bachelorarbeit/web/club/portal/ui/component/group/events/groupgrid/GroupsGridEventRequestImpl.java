package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import lombok.Data;

@Data
public class GroupsGridEventRequestImpl implements GroupsGridEventRequest {

    private final Group group;


    @Override
    public Group getClickedGroup() {
        return this.getGroup();
    }
}
