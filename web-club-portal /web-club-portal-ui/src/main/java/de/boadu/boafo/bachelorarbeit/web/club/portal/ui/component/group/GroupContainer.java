package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.repository.GroupRequestRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.PersonUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid.GroupGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupgrid.GroupsGridEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.groupscontainer.GroupContainerEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsmenubar.GroupMenubarEventListeners;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog.GroupDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.events.traininggroupsdialog.GroupDialogEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.fw.Broadcaster;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupContainer extends AbstractComponent implements GroupMenubarEventListeners,
        GroupDialogEventListener, GroupGridEventListener, AbstractObserver<GroupContainerEventListener> {

    private HorizontalLayout componentRootLayout;

    private final GroupListComponent groupListComponent;

    private final GroupMenubarComponent groupMenubarComponent;

    private final GroupGridComponent groupsGridCOmponent;

    private VerticalLayout groupLayout;

    private final GroupDialogFormComponent groupDialogFormComponent;

    private final AbstractObserver<GroupDialogEventListener> groupsDialogEventListenerObserver;

    private final AbstractObserver<GroupMenubarEventListeners> groupsMenuBarEventListenersObserver;

    private final AbstractObserver<GroupGridEventListener> groupGridEventListenerObserver;

    private final GroupUiService groupUiService;

    private final PersonUiService personUiService;

    private final SecurityService securityService;

    private Group clickGroup;

    private final GroupRequestRepository groupRequestRepository;

    private final Set<GroupContainerEventListener> eventListener;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachGroupMenubarEventListener();
        this.attachGroupDialogEventListeners();
        this.attachGroupGridEventListener();
        this.intializeGroupLayout();
        this.intializeComponentRootLayout();

    }

    private void attachGroupMenubarEventListener(){

        this.getGroupsMenuBarEventListenersObserver().addEventListeners(this);

    }

    private void attachGroupDialogEventListeners(){

        this.getGroupsDialogEventListenerObserver().addEventListeners(this);

    }

    private void attachGroupGridEventListener(){

        this.getGroupGridEventListenerObserver().addEventListeners(this);

    }

    private void intializeGroupLayout(){

        this.groupLayout = new VerticalLayout();

        this.getGroupLayout().add(this.getGroupMenubarComponent());
        this.getGroupLayout().add(this.getGroupsGridCOmponent());

    }

    private void intializeComponentRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getGroupListComponent());
        this.getComponentRootLayout().add(this.getGroupLayout());
        this.getComponentRootLayout().add(this.getGroupDialogFormComponent());

        boolean hasRoleTrainer = this.getSecurityService().getUserRoles().contains("ROLE_TRAINER");

        if(hasRoleTrainer) {

            this.getGroupMenubarComponent().setVisible(true);

        }

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleButtonAdd() {

        this.getGroupDialogFormComponent().openDialog();

    }

    @Override
    public void handleButtonJoin() {

        if(this.getClickGroup() != null ){

            Long userId = this.getSecurityService().getUserId();
            String userSurname = this.getSecurityService().getLoggedUser().getSurname();
            String userName = this.getSecurityService().getLoggedUser().getName();

            Long groupId = this.getClickGroup().getId();
            Long adminId = this.getClickGroup().getAdminId();
            String groupName = this.getClickGroup().getName();

            MutableGroupRequest request = new GroupRequestsDTO();
            request.setGroupId(groupId);
            request.setRequesterId(userId);
            request.setGroupName(groupName);
            request.setAdminId(adminId);
            request.setName(userName);
            request.setSurname(userSurname);

            this.getGroupUiService().addGroupRequest(groupId, request);

            this.getGroupMenubarComponent().disableJoinButton();

            List<GroupRequest> traininGroupRequestByTrainer = this.getGroupUiService().getGroupRequestByTrainer(adminId);
            Broadcaster.broadcastGroupRequestListener(traininGroupRequestByTrainer);

        }

    }

    @Override
    public void handleButtonSave(GroupDialogEventRequest event) {

        Long userId = this.getSecurityService().getUserId();

        MutableGroup newGroupToCreate = event.getNewGroupToCreate();

        Group group = this.getGroupUiService().createTrainingGroup(newGroupToCreate);
        this.getPersonUiService().addNewGroupToUser(userId, (MutableGroup) group);

        Set<Group> userGroups = this.getPersonUiService().getUserGroups(userId);
        this.getGroupListComponent().refreshList(userGroups);

        List<Group> groups = this.getGroupUiService().getTrainingGroups();
        Broadcaster.broadcast(groups);

    }

    @Override
    public void handleGridClick(GroupsGridEventRequest event) {

        this.clickGroup = event.getClickedGroup();

        Long userId = this.getSecurityService().getUserId();

        Set<Group> userGroups = this.getPersonUiService().getUserGroups(userId);

        if(userGroups.contains(this.getClickGroup())) {

            this.getGroupMenubarComponent().disableJoinButton();

        } else {

            /*TODO: nicht das repository direkt ansprechen*/
            List<GroupRequestsDTO> groupRequestsByRequesterIdAndGroupId = this.getGroupRequestRepository()
                    .getGroupRequestsByRequesterIdAndGroupId(userId, this.getClickGroup().getId());

            if(groupRequestsByRequesterIdAndGroupId.size() != 0){

                this.getGroupMenubarComponent().disableJoinButton();
                this.getGroupMenubarComponent().setJoinButtonToRequested();

            } else {

                this.getGroupMenubarComponent().disButtonRequested();
                this.getGroupMenubarComponent().showJoinButton();

            }
        }
    }

    @Override
    public void addEventListeners(GroupContainerEventListener listener) {

        this.getEventListener().add(listener);

    }

    public void refreshData() {

        List<Group> trainingGroups = this.getGroupUiService().getTrainingGroups();

        this.getGroupsGridCOmponent().clearData();
        this.getGroupsGridCOmponent().refreshGrid(trainingGroups);

    }
}
