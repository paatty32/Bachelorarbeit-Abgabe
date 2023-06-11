package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.MutableTrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingsDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan.TrainingGroupPlanEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplan.TrainingGroupPlanEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.group.traininggroup.events.traininggroupplanmenubar.TrainingGroupPlanMenubarEventListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingGroupPlanContainer extends AbstractComponent implements TrainingGroupPlanEventListener, TrainingGroupPlanMenubarEventListener {

    private VerticalLayout componentRootLayout;

    private final TrainingGroupPlanMenubarComponent trainingPlanMenubarComponent;

    private final TrainingGroupPlanComponent trainingPlanComponent;

    private final SecurityService securityService;

    private final AbstractObserver<TrainingGroupPlanEventListener>trainingPlanEventListenerObserver;

    private final AbstractObserver<TrainingGroupPlanMenubarEventListener> trainingGroupPlanMenubarEventListenerObserver;

    private final TrainingsDiaryUiService trainingsDiaryUiService;

    private TrainingPlanEntry clickedEntry;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {


    }

    @Override
    protected void initializeComponents() {

        this.attachTrainingPlanEventListener();
        this.attachTrainingGroupPlanMenubarEventlistener();
        this.initializeComponentRootLayout();

    }

    private void attachTrainingPlanEventListener(){

        this.getTrainingPlanEventListenerObserver().addEventListeners(this);

    }

    private void attachTrainingGroupPlanMenubarEventlistener(){

        this.getTrainingGroupPlanMenubarEventListenerObserver().addEventListeners(this);

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getTrainingPlanMenubarComponent().setVisible(false);

        this.getComponentRootLayout().add(this.getTrainingPlanMenubarComponent());
        this.getComponentRootLayout().add(this.getTrainingPlanComponent());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleGridClick(TrainingGroupPlanEventRequest clickedEntry) {

        this.clickedEntry = clickedEntry.getEntry();

        boolean hasRoleAthlete = this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE");

        if(hasRoleAthlete) {
            this.getTrainingPlanMenubarComponent().setVisible(true);
        }
    }

    public void setGroupId(Long parameter) {
        this.getTrainingPlanComponent().setGroupId(parameter);
    }

    @Override
    public void handleButtonAdd() {

        Long userId = this.getSecurityService().getUserId();

        TrainingPlanEntry entry = this.getClickedEntry();

        MutableTrainingDiaryEntry mutableTrainingDiaryEntry = new TrainingDiaryEntryDTO();
        mutableTrainingDiaryEntry.setDate(entry.getDate());
        mutableTrainingDiaryEntry.setSession(entry.getSession());

        this.getTrainingsDiaryUiService().addNewTrainingDiaryEntry(userId, (TrainingDiaryEntry) mutableTrainingDiaryEntry);

    }
}
