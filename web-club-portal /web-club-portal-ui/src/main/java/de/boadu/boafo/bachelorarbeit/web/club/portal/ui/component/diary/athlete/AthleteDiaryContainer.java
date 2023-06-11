package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.AthleteDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events.AthleteDiaryGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.athlete.events.AthleteDiaryGridEventRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class AthleteDiaryContainer extends AbstractComponent implements AthleteDiaryGridEventListener {

    private HorizontalLayout componentRootLayout;

    private final AthleteDiaryGridComponent athleteDiaryGridComponent;

    private final AthleteDiaryEntryGridComponent athleteDiaryEntryGridComponent;

    private final AbstractObserver<AthleteDiaryGridEventListener> athleteDiaryGridEventListenerObserver;

    private final AthleteDiaryUiService athleteDiaryUiService;

    private final SecurityService securityService;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

    }

    @Override
    protected void initializeComponents() {

        this.attachAthleteDiaryGridEventListener();
        this.initializeComponentRootLayout();

    }

    private void attachAthleteDiaryGridEventListener(){

        this.getAthleteDiaryGridEventListenerObserver().addEventListeners(this);

    }

    private void initializeComponentRootLayout(){

        this.getAthleteDiaryEntryGridComponent().setVisible(false);

        this.componentRootLayout = new HorizontalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getAthleteDiaryGridComponent());
        this.getComponentRootLayout().add(this.getAthleteDiaryEntryGridComponent());

    }

    @Override
    protected void initializeComponentsActions() {

    }

    @Override
    public void handleGridClick(AthleteDiaryGridEventRequest event) {

        AppUser clickedAppUser = event.getClickedPerson();
        Long clickedPersonId = clickedAppUser.getId();

        Long trainerId = this.getSecurityService().getUserId();

        List<TrainingDiaryEntry> entries = this.getAthleteDiaryUiService().getEntriesFromAthlete(clickedPersonId, trainerId);

        this.getAthleteDiaryEntryGridComponent().clearData();
        this.getAthleteDiaryEntryGridComponent().refreshGrid(entries);
        this.getAthleteDiaryEntryGridComponent().setVisible(true);

    }

    public void refreshData() {

        Long userId = this.getSecurityService().getUserId();

        List<AppUser> appUserList = this.getAthleteDiaryUiService().getAthletesByTrainer(userId);

        this.getAthleteDiaryGridComponent().clearData();
        this.getAthleteDiaryGridComponent().refreshGrid(appUserList);

    }
}
