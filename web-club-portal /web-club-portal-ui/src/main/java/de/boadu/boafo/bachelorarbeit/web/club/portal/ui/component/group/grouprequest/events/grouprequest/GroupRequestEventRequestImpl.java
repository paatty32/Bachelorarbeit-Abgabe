package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequest;
import lombok.Data;

@Data
public class GroupRequestEventRequestImpl implements GroupRequestEventRequest {

    private final GroupRequest request;

    @Override
    public GroupRequest getGroupRequest() {
        return this.getRequest();
    }
}
