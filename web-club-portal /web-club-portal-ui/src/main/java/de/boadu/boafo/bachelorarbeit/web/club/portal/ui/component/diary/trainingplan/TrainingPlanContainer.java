package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.GroupUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingPlanEntryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingPlanUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplandialogform.TrainingPlanDialogFormEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplandialogform.TrainingPlanDialogFormEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform.TrainingPlanFormDeleteEntryEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform.TrainingPlanFormEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanform.TrainingPlanFormEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplangrid.TrainingPlanGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplangrid.TrainingPlanGridEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare.TrainingPlanShareEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplanshare.TrainingPlanShareEventRequest;
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
public class TrainingPlanContainer extends AbstractComponent implements TrainingPlanGridEventListener,
        TrainingPlanDialogFormEventListener, TrainingPlanFormEventListener, TrainingPlanShareEventListener {

    private HorizontalLayout componentRootLayout;

    private final TrainingPlanGridComponent trainingPlanGridComponent;

    private final TrainingPlanFormComponent trainingPlanFormComponent;

    private final TrainingPlanDialogFormComponent trainingPlanDialogFormComponent;

    private final TrainingPlanShareDialogComponent trainingPlanShareDialogComponent;

    private final AbstractObserver<TrainingPlanDialogFormEventListener> trainingPlanDialogFormComponentObserver;

    private final AbstractObserver<TrainingPlanGridEventListener> trainingPlanGridObserver;

    private final AbstractObserver<TrainingPlanFormEventListener> trainingPlanFormEventListenerObserver;

    private final AbstractObserver<TrainingPlanShareEventListener> trainingPlanShareEventListenerObserver;

    private final SecurityService securityService;

    private final TrainingPlanUiService trainingPlanUiService;

    private final GroupUiService groupUiService;

    private final TrainingPlanEntryUiService trainingPlanEntryUiService;

    private TrainingPlanEntry clickedTrainingPlanEntry;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachTrainingPlanGridEventListener();
        this.attachTrainingPlanDialogFormEventListener();
        this.attachTrainingPlanFormEventListener();
        this.attachTrainingPlanShareDialogEventListener();
        this.initializeComponentRootLayout();

    }

    private void attachTrainingPlanGridEventListener(){

        this.getTrainingPlanGridObserver().addEventListeners(this);

    }

    private void attachTrainingPlanFormEventListener(){

        this.getTrainingPlanFormEventListenerObserver().addEventListeners(this);

    }

    private void attachTrainingPlanDialogFormEventListener(){

        this.getTrainingPlanDialogFormComponentObserver().addEventListeners(this);

    }

    private void attachTrainingPlanShareDialogEventListener(){

        this.getTrainingPlanShareEventListenerObserver().addEventListeners(this);

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getTrainingPlanGridComponent());
        this.getComponentRootLayout().add(this.getTrainingPlanFormComponent());
        this.getComponentRootLayout().add(this.getTrainingPlanDialogFormComponent());
        this.getComponentRootLayout().add(this.getTrainingPlanShareDialogComponent());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleButtonAdd() {

        this.getTrainingPlanDialogFormComponent().openDialog();

    }

    @Override
    public void handleGridClick(TrainingPlanGridEventRequest event) {

        this.clickedTrainingPlanEntry = event.getTrainingPlanEntry();

        this.getTrainingPlanFormComponent().fillForm(clickedTrainingPlanEntry);
        this.getTrainingPlanFormComponent().setClickedEntryId(clickedTrainingPlanEntry);

        this.getTrainingPlanFormComponent().setVisible(true);

    }

    @Override
    public void handleButtonSave(TrainingPlanDialogFormEventRequest event) {

        MutableTrainingPlanEntry newEntry = event.getNewEntry();

        Long userId = this.getSecurityService().getUserId();

        this.getTrainingPlanUiService().addnewTrainingPlanEntry(userId, newEntry);
        List<TrainingPlanEntry> trainingPlanEntriesByUser = this.getTrainingPlanUiService().getTrainingPlanEntriesByUser(userId);

        this.getTrainingPlanGridComponent().clearData();
        this.getTrainingPlanGridComponent().refreshGrid(trainingPlanEntriesByUser);

    }

    @Override
    public void handleButtonUpdate(TrainingPlanFormEventRequest event) {

        MutableTrainingPlanEntry entry = event.getUpdatedEntry();

        this.getTrainingPlanUiService().updateEntry(entry);

        Long userId = this.getSecurityService().getUserId();

        List<TrainingPlanEntry> trainingPlanEntriesUpdated = this.getTrainingPlanUiService().getTrainingPlanEntriesByUser(userId);

        this.getTrainingPlanGridComponent().clearData();
        this.getTrainingPlanGridComponent().refreshGrid(trainingPlanEntriesUpdated);

    }

    @Override
    public void handleButtonDelete(TrainingPlanFormDeleteEntryEventRequest event) {

        Long userId = this.getSecurityService().getUserId();

        Long deleteEntryId = event.getDeleteEntryId();

        TrainingPlanEntry entry = this.getTrainingPlanEntryUiService().getEntry(deleteEntryId);
        Set<GroupDTO> groups = entry.getGroups();

        this.groupUiService.deleteGroupTraininingPlanEntry(groups, entry);

        this.getTrainingPlanUiService().deleteTrainingPlanEntry(userId, deleteEntryId);

        List<TrainingPlanEntry> trainingPlanEntriesUpdated = this.getTrainingPlanUiService().getTrainingPlanEntriesByUser(userId);

        this.getTrainingPlanGridComponent().clearData();
        this.getTrainingPlanGridComponent().refreshGrid(trainingPlanEntriesUpdated);

        this.getTrainingPlanFormComponent().setVisible(false);

    }

    @Override
    public void handleButtonShare() {

        this.getTrainingPlanShareDialogComponent().openDialog();



    }

    @Override
    public void handleButtonShare(TrainingPlanShareEventRequest event) {

        Set<Group> selectedGroups = event.getSelectedGroups();

        MutableTrainingPlanEntry newEntry = new TrainingPlanEntryDTO();
        newEntry.setId(this.getClickedTrainingPlanEntry().getId());
        newEntry.setDate(this.getClickedTrainingPlanEntry().getDate());
        newEntry.setSession(this.getClickedTrainingPlanEntry().getSession());
        newEntry.setAthlete(this.getClickedTrainingPlanEntry().getAthlete());

        if(this.getClickedTrainingPlanEntry() != null) {

            for (Group group : selectedGroups) {

                Long groupId = group.getId();

                this.getGroupUiService().addTrainingPlanEntry(groupId, newEntry);

            }

        }

    }

    public void refreshData() {

        Long userId = this.getSecurityService().getUserId();

        List<TrainingPlanEntry> trainingPlanEntriesUpdated = this.getTrainingPlanUiService().getTrainingPlanEntriesByUser(userId);

        this.getTrainingPlanGridComponent().clearData();
        this.getTrainingPlanGridComponent().refreshGrid(trainingPlanEntriesUpdated);

        this.getTrainingPlanFormComponent().setVisible(false);

    }
}
