package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.AthleteDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.PersonUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest.GroupRequestEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequest.GroupRequestEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequestcontainer.GroupRequestContainerEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.grouprequest.events.grouprequestmenubar.GroupRequestMenubarEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw.Broadcaster;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupRequestContainer extends AbstractComponent implements GroupRequestEventListener,
        GroupRequestMenubarEventListener, AbstractObserver<GroupRequestContainerEventListener> {

    private VerticalLayout componentRootLayout;

    private final GroupRequestGridComponent groupRequestGridComponent;

    private final GroupRequestMenubarComponent groupRequestMenubarComponent;

    private final AbstractObserver<GroupRequestEventListener> trainingGroupRequestEventListenerObserver;

    private final AbstractObserver<GroupRequestMenubarEventListener> trainingGroupRequestMenubarEventListenerObserver;

    private final GroupUiService groupUiService;

    private final PersonUiService personUiService;

    private final AthleteDiaryUiService athleteDiaryUiService;

    private GroupRequest clickedGroupRequest;

    private final SecurityService securityService;

    private Set<GroupRequestContainerEventListener> groupRequestContainerEventListener;



    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.groupRequestContainerEventListener = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.attachTrainingGroupRequestEventListener();
        this.attachTrainingGroupRequestMenubarEventListener();
        this.initializeRootLayout();

    }



    private void attachTrainingGroupRequestEventListener(){

        this.getTrainingGroupRequestEventListenerObserver().addEventListeners(this);

    }

    private void attachTrainingGroupRequestMenubarEventListener(){

        this.getTrainingGroupRequestMenubarEventListenerObserver().addEventListeners(this);

    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getGroupRequestMenubarComponent().setVisible(false);

        this.getComponentRootLayout().add(this.getGroupRequestMenubarComponent());
        this.getComponentRootLayout().add(this.getGroupRequestGridComponent());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleGridClick(GroupRequestEventRequest event) {

        this.clickedGroupRequest = event.getGroupRequest();

        this.getGroupRequestMenubarComponent().setVisible(true);

    }

    @Override
    public void handleButtonAccept() {

        if(this.getClickedGroupRequest() != null){

            GroupRequest groupRequest = this.getClickedGroupRequest();
            Long requesterId = groupRequest.getRequesterId();
            Long groupId = groupRequest.getGroupId();
            Long adminId = groupRequest.getAdminId();

            Group groupById = this.getGroupUiService().getTrainingGroupById(groupId);

            this.getPersonUiService().addNewGroupToUser(requesterId, (MutableGroup) groupById);

            this.getGroupUiService().deleteGroupRequestById(requesterId, groupId);

            List<GroupRequest> traininGroupRequestByTrainer = this.getGroupUiService().getGroupRequestByTrainer(adminId);

            this.getGroupRequestGridComponent().clearData();
            this.getGroupRequestGridComponent().refreshGrid(traininGroupRequestByTrainer);

            this.getGroupRequestMenubarComponent().setVisible(false);

            this.notifyEventListener();

            Long userId = this.getSecurityService().getUserId();

            Set<Group> userGroups = this.getPersonUiService().getUserGroups(userId);
            List<Group> userGroupsList = new ArrayList<>(userGroups);

            Broadcaster.broadCastOwnTrainingGroupListener(userGroupsList);

            this.getAthleteDiaryUiService().createAthleteDiary(groupId, adminId, requesterId);

        }

    }



    @Override
    public void handleButtonDecline() {

        if(this.getClickedGroupRequest() != null){

            GroupRequest groupRequest = this.getClickedGroupRequest();
            Long requesterId = groupRequest.getRequesterId();
            Long groupId = groupRequest.getGroupId();
            Long adminId = groupRequest.getAdminId();

            this.getGroupUiService().deleteGroupRequestById(requesterId, groupId);

            List<GroupRequest> traininGroupRequestByTrainer = this.getGroupUiService().getGroupRequestByTrainer(adminId);

            this.getGroupRequestGridComponent().clearData();
            this.getGroupRequestGridComponent().refreshGrid(traininGroupRequestByTrainer);

            this.getGroupRequestMenubarComponent().setVisible(false);

            this.notifyEventListener();

        }

    }

    public void clearData() {
        this.getGroupRequestGridComponent().clearData();

    }

    public void refreshGrid(List<GroupRequest> trainingGroupRequest) {
        this.getGroupRequestGridComponent().refreshGrid(trainingGroupRequest);
    }

    @Override
    public void addEventListeners(GroupRequestContainerEventListener listener) {
        this.getGroupRequestContainerEventListener().add(listener);
    }

    private void notifyEventListener() {
        this.getGroupRequestContainerEventListener().forEach(listener -> listener.handleRequestAnswer());

    }

    public void refreshData() {

        Long userId = this.getSecurityService().getUserId();

        List<GroupRequest> traininGroupRequestByTrainer = this.getGroupUiService().getGroupRequestByTrainer(userId);

        this.clearData();

        this.refreshGrid(traininGroupRequestByTrainer);

    }
}
