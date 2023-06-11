package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.AthleteDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingsDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.AthleteDiaryContainer;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.CompetitionDiaryContainer;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.TrainingDiaryFormComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.TrainingDiaryGridComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.TrainingDiaryShareDialogComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.TrainingsDiaryFormDialogComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridClickedEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarydialog.TrainingsDairyFormDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarydialog.TrainingsDairyFormDialogSaveClickedEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform.TrainingsDairyFormEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform.TrainingsDiaryDeleteEntryEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiaryform.TrainingsDiaryFormEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog.TrainingDiaryShareDialogEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.trainingdiarysharedialog.TrainingDiaryShareDialogEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.TrainingPlanContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class DiaryTabContainer extends AbstractComponent implements TrainingsDiaryGridEventListener,
        TrainingsDairyFormEventListener, TrainingsDairyFormDialogEventListener, TrainingDiaryShareDialogEventListener {

    private VerticalLayout componentRootLayout;

    private Tabs diary;
    private Tab trainingTab;
    private Tab competitionTab;
    private Tab trainingPlanTab;
    private Tab athleteTab;

    private HorizontalLayout tabContent;

    private final TrainingDiaryGridComponent trainingDiaryGridComponent;

    private final CompetitionDiaryContainer competitionDiaryContainer;

    private final TrainingPlanContainer trainingplanContainer;

    private final TrainingDiaryFormComponent trainingDiaryFormComponent;

    private final TrainingsDiaryFormDialogComponent trainingsDiaryFormDialogComponent;

    private final TrainingDiaryShareDialogComponent trainingDiaryShareDialogComponent;

    private final AthleteDiaryContainer athleteDiaryContainer;

    private final AbstractObserver<TrainingsDiaryGridEventListener> trainingsDiaryObserver;

    private final AbstractObserver<TrainingsDairyFormEventListener> trainingsDairyFormEventListenerObserver;

    private final AbstractObserver<TrainingsDairyFormDialogEventListener> trainingsDairyFormDialogEventListenerObserver;

    private final AbstractObserver<TrainingDiaryShareDialogEventListener> trainingDiaryShareDialogEventListenerObserver;

    private final TrainingsDiaryUiService trainingsDiaryUiService;

    private final AthleteDiaryUiService athleteDiaryUiService;

    private final SecurityService securityService;

    private TrainingDiaryEntry clickedEntry;

    private void attachListener(){

        this.getTrainingsDiaryObserver().addEventListeners(this);

    }

    private void attachTrainingsDiaryFormEventListener(){

        this.getTrainingsDairyFormEventListenerObserver().addEventListeners(this);

    }

    private void attachTrainingsDiaryDialogFormEventListener(){

        this.getTrainingsDairyFormDialogEventListenerObserver().addEventListeners(this);

    }

    private void attachTrainingDiaryShareDialogEventListener(){

        this.getTrainingDiaryShareDialogEventListenerObserver().addEventListeners(this);
    }

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachListener();
        this.attachTrainingsDiaryFormEventListener();
        this.attachTrainingsDiaryDialogFormEventListener();
        this.attachTrainingDiaryShareDialogEventListener();
        this.initializeTabComponent();
        this.intializeTabContent();
        this.initializeComponentRootLayout();

    }

    private void initializeTabComponent(){

        List<String> userRoles = this.getSecurityService().getUserRoles();

        this.trainingTab = new Tab("Training");
        this.competitionTab = new Tab("Wettkampf");
        this.trainingPlanTab = new Tab("Trainingsplan");
        this.athleteTab = new Tab("Athleten");

        this.diary = new Tabs();

        if(userRoles.contains("ROLE_ATHLETE")){

            this.diary.add(this.getTrainingTab());
            this.diary.add(this.getCompetitionTab());

        }

        if(userRoles.contains("ROLE_TRAINER")){

            this.diary.add(this.getAthleteTab());
            this.diary.add(this.getTrainingPlanTab());

        }



        this.tabContent = new HorizontalLayout();
        this.tabContent.setSizeFull();

    }

    private void intializeTabContent(){

        boolean role_athlete = this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE");
        boolean role_trainer = this.getSecurityService().getUserRoles().contains("ROLE_TRAINER");

        if(role_athlete && role_trainer){

            this.getTabContent().add(this.getTrainingDiaryGridComponent());
            this.getTabContent().add(this.getTrainingDiaryFormComponent());
            this.getTabContent().add(this.getTrainingDiaryShareDialogComponent());

        } else if(role_athlete){

            this.getTabContent().add(this.getTrainingDiaryGridComponent());
            this.getTabContent().add(this.getTrainingDiaryFormComponent());
            this.getTabContent().add(this.getTrainingDiaryShareDialogComponent());

        } else if(role_trainer){

            this.getTabContent().add(this.getAthleteDiaryContainer());

        }

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getDiary());
        this.getComponentRootLayout().add(this.getTabContent());
        this.getComponentRootLayout().add(this.getTrainingsDiaryFormDialogComponent());
        this.getComponentRootLayout().setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.getDiary());

    }

    private void setTabContent(Tab selectedTab) {

        this.getTabContent().removeAll();

        switch (selectedTab.getLabel()){
            case "Training":
                this.getTabContent().add(this.getTrainingDiaryGridComponent());
                this.getTabContent().add(this.getTrainingDiaryFormComponent());
                break;

            case "Wettkampf":
                this.getTabContent().add(this.getCompetitionDiaryContainer());
                break;

            case "Trainingsplan":
                this.getTabContent().add(this.getTrainingplanContainer());
                break;

            case "Athleten":
                this.getTabContent().add(this.getAthleteDiaryContainer());
                break;
        }

    }

    @Override
    protected void initializeComponentsActions() {

        this.getDiary().addSelectedChangeListener(
                event -> this.setTabContent(event.getSelectedTab())
        );
    }

    @Override
    public void handleClickGrid(TrainingsDiaryGridClickedEventRequest event) {

        this.getTrainingDiaryFormComponent().setVisible(true);

        this.clickedEntry = event.getEntry();
        Long entryId = clickedEntry.getId();

        String session = clickedEntry.getSession();

        String feeling = clickedEntry.getFeeling();
         if(feeling == null){
            feeling = "";
        }

        LocalDate date = clickedEntry.getDate();

        Set<AthleteDiaryDto> athleteDiaries = clickedEntry.getAthleteDiaries();

        this.getTrainingDiaryFormComponent().setForm(entryId, session, feeling, date, athleteDiaries);

    }

    @Override
    public void handleClickAdd() {

        this.getTrainingsDiaryFormDialogComponent().openDialog();

        this.getTrainingDiaryGridComponent().refreshGrid();

    }

    @Override
    public void handleButtonUpdate(TrainingsDiaryFormEventRequest event) {

        TrainingDiaryEntry updatedEntry = event.getEntry();

        System.out.println("Updated Entry: " + updatedEntry.getSession() + " " + updatedEntry.getFeeling() + updatedEntry.getDate());

        this.getTrainingsDiaryUiService().updateEntry(updatedEntry);

        this.getTrainingDiaryGridComponent().refreshGrid();

        this.clickedEntry = updatedEntry;
    }

    @Override
    public void handleButtonDelete(TrainingsDiaryDeleteEntryEventRequest event) {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;
        Long userId = currentPersonDTO.getId();

        Long clickedEntryId = event.getClickedEntryId();

        this.getTrainingsDiaryUiService().deleteTrainingEntry(userId, clickedEntryId);

        this.getTrainingDiaryGridComponent().refreshGrid();

        this.getTrainingDiaryFormComponent().setVisible(false);
    }

    @Override
    public void handleButtonShare() {

        this.getTrainingDiaryShareDialogComponent().iniializeDialog();
        this.getTrainingDiaryShareDialogComponent().openDialog();

    }

    @Override
    public void handleSave(TrainingsDairyFormDialogSaveClickedEventRequest event) {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;
        long userId = currentPersonDTO.getId();

        TrainingDiaryEntry newEntry = event.getEntry();

        this.getTrainingsDiaryUiService().addNewTrainingDiaryEntry(userId, newEntry);

        this.getTrainingDiaryFormComponent().setVisible(false);

        this.getTrainingDiaryGridComponent().refreshGrid();

    }

    @Override
    public void handleClickSelect(TrainingDiaryShareDialogEventRequest event) {

        Set<AppUser> trainer = event.getTrainer();

        Long athleteId = this.getSecurityService().getUserId();

        if(this.getClickedEntry() != null){

            TrainingDiaryEntry clickedEntry1 = this.getClickedEntry();

            System.out.println(clickedEntry1.getDate() + " " + clickedEntry1.getSession() + " " + clickedEntry1.getFeeling());

            this.getAthleteDiaryUiService().addAthleteEntry(trainer, athleteId, clickedEntry1);

        }


    }

    public void refreshData() {

        boolean role_athlete = this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE");

        boolean role_trainer = this.getSecurityService().getUserRoles().contains("ROLE_TRAINER");

        if(role_athlete) {
            this.getTrainingDiaryGridComponent().refreshGrid();

            this.getCompetitionDiaryContainer().refreshData();
        }

        if(role_trainer){

            this.getAthleteDiaryContainer().refreshData();

            this.getTrainingplanContainer().refreshData();

        }
    }
}
