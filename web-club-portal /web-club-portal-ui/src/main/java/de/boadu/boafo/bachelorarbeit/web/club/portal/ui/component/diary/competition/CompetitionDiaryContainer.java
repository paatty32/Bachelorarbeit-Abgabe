package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.CompetitionDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform.CompetitionDiaryFormDeleteEntryEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform.CompetitionDiaryFormEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryform.CompetitionDiaryFormEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog.CompetitionDiaryFormDialogEventListeners;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiaryformdialog.CompetitonDiaryFormDialogEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid.CompetitionDiaryGridClickEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid.CompetitionDiaryGridEventListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class CompetitionDiaryContainer extends AbstractComponent implements CompetitionDiaryGridEventListener,
        CompetitionDiaryFormDialogEventListeners, CompetitionDiaryFormEventListener {

    private HorizontalLayout componentRootLayout;

    private final CompetitionDiaryGridComponent competitionDiaryComponent;

    private final CompetitionDiaryFormDialogComponent competitionDiaryFormDialogComponent;

    private final CompetitionDiaryFormComponent competitionDiaryFormComponent;

    private final AbstractObserver<CompetitionDiaryGridEventListener> competitionDiaryGridObserver;

    private final AbstractObserver<CompetitionDiaryFormDialogEventListeners> competitionDiaryFormDialogObserver;

    private final AbstractObserver<CompetitionDiaryFormEventListener> competitionDiaryFormComponentObserver;

    private final SecurityService securityService;

    private final CompetitionDiaryUiService competitionDiaryUiService;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachCompetitionDiaryGridEventListener();
        this.attachCompetitionDiaryFormDialogEventlistener();
        this.attachCompetitionDiaryFormEventListener();
        this.initializeComponentRootLayout();

    }

    private void attachCompetitionDiaryGridEventListener(){

        this.getCompetitionDiaryGridObserver().addEventListeners(this);

    }

    private void attachCompetitionDiaryFormDialogEventlistener(){

        this.getCompetitionDiaryFormDialogObserver().addEventListeners(this);

    }

    private void attachCompetitionDiaryFormEventListener(){

        this.getCompetitionDiaryFormComponentObserver().addEventListeners(this);
    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getCompetitionDiaryComponent());
        this.getComponentRootLayout().add(this.getCompetitionDiaryFormComponent());
        this.getComponentRootLayout().add(this.getCompetitionDiaryFormDialogComponent());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleButtonAdd() {

        this.getCompetitionDiaryFormDialogComponent().openDialog();

    }

    @Override
    public void handleGridClick(CompetitionDiaryGridClickEventRequest event) {

        this.getCompetitionDiaryFormComponent().setVisible(true);

        CompetitionDiaryEntry clickedEntry = event.getClickedEntry();

        LocalDate date = event.getClickedEntry().getDate();
        String place = event.getClickedEntry().getPlace();
        String dicipline = event.getClickedEntry().getDicipline();
        String result = event.getClickedEntry().getResult();
        String feeling = event.getClickedEntry().getFeeling();

        this.getCompetitionDiaryFormComponent().setForm(clickedEntry);

    }

    @Override
    public void handleSave(CompetitonDiaryFormDialogEventRequest event) {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO loggedPersonDTO = (AppUserDTO) authenticatedUser;
        Long loggedPersonId = loggedPersonDTO.getId();

        MutableCompetitionDiaryEntry newEntry = event.getEntry();

        this.getCompetitionDiaryUiService().addNewDiaryEntry(loggedPersonId, newEntry);

        this.getCompetitionDiaryComponent().refreshGrid();

    }

    @Override
    public void handleButtonDelete(CompetitionDiaryFormDeleteEntryEventRequest event) {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;
        Long currentPersonId = currentPersonDTO.getId();

        Long deleteEntry = event.getClickedEntryId();

        this.getCompetitionDiaryUiService().deleteEntry(currentPersonId, deleteEntry);

        this.getCompetitionDiaryFormComponent().clearForm();

        this.getCompetitionDiaryFormComponent().setVisible(false);

        this.getCompetitionDiaryComponent().refreshGrid();

    }

    @Override
    public void handleButtonUpdate(CompetitionDiaryFormEventRequest event) {

        MutableCompetitionDiaryEntry updatedEntry = event.getClickedEntry();

        CompetitionDiaryEntryDto updatedCompEntry = this.getCompetitionDiaryUiService().upadeEntry(updatedEntry);

        if(updatedCompEntry != null) {

            this.getCompetitionDiaryComponent().refreshGrid();

        }
    }

    public void refreshData() {

        this.getCompetitionDiaryComponent().refreshGrid();

    }
}
