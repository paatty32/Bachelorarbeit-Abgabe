package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.CompetitionDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid.CompetitionDiaryGridClickEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid.CompetitionDiaryGridClickEventRequestImpl;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.competition.events.competitiondiarygrid.CompetitionDiaryGridEventListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class CompetitionDiaryGridComponent extends AbstractComponent implements AbstractObserver<CompetitionDiaryGridEventListener> {

    private VerticalLayout componentRootLayout;

    private Button btnAdd;

    private DatePicker datePicker;

    private TextField tfSearchPlace;

    private TextField tfSearchDiscipline;

    private Grid<CompetitionDiaryEntry> competitionDiaryEntryGrid;
    private List<CompetitionDiaryEntry> competitionDiaryEntryBuffer;
    private InMemoryDataProvider<CompetitionDiaryEntry> competitionDiaryEntryInMemoryDataProvider;

    private final CompetitionDiaryUiService competitionDiaryUiService;

    private final SecurityService securityService;

    private Set<CompetitionDiaryGridEventListener> eventListeners;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.competitionDiaryEntryBuffer = new ArrayList<>();

        this.eventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        if(this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE")) {

            this.initializeGrid();
            this.initializeGridData();
            this.initializeRootLayout();

        } else {

            this.initializeGrid();
            this.initializeRootLayout();

        }

    }

    private void initializeGrid(){

        this.competitionDiaryEntryGrid = new Grid<>();
        this.competitionDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.competitionDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.competitionDiaryEntryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        Grid.Column<CompetitionDiaryEntry> competitionDiaryDateColumn = this.competitionDiaryEntryGrid.addColumn(CompetitionDiaryEntry::getDate).setHeader("Datum");
        Grid.Column<CompetitionDiaryEntry> competitionDiaryPlaceColumn = this.competitionDiaryEntryGrid.addColumn(CompetitionDiaryEntry::getPlace).setHeader("Ort");
        Grid.Column<CompetitionDiaryEntry> competitionDiaryDisciplineColumn = this.competitionDiaryEntryGrid.addColumn(CompetitionDiaryEntry::getDicipline).setHeader("Disziplin");
        Grid.Column<CompetitionDiaryEntry> competitionDiaryResaultColumn = this.competitionDiaryEntryGrid.addColumn(CompetitionDiaryEntry::getResult).setHeader("Ergebnis");
        Grid.Column<CompetitionDiaryEntry> competitionDiaryFeelingColumn = this.competitionDiaryEntryGrid.addColumn(CompetitionDiaryEntry::getFeeling).setHeader("Zufriedenheit");

        Grid.Column<CompetitionDiaryEntry> shareIconColumn = this.competitionDiaryEntryGrid.addComponentColumn(entry -> new Button(VaadinIcon.SHARE.create()));

        this.btnAdd = new Button();
        this.btnAdd.setText("Hinzufügen");
        this.btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        this.datePicker = new DatePicker();
        this.datePicker.setLocale(Locale.GERMANY);
        this.datePicker.setClearButtonVisible(true);
        this.datePicker.setWidth("150px");

        this.tfSearchPlace = new TextField();
        this.tfSearchPlace.setClearButtonVisible(true);
        this.tfSearchPlace.setPrefixComponent(VaadinIcon.SEARCH.create());
        this.tfSearchPlace.setWidth("150px");

        this.tfSearchDiscipline = new TextField();
        this.tfSearchDiscipline.setClearButtonVisible(true);
        this.tfSearchDiscipline.setPrefixComponent(VaadinIcon.SEARCH.create());
        this.tfSearchDiscipline.setWidth("150px");

        HeaderRow headerRow = this.getCompetitionDiaryEntryGrid().appendHeaderRow();
        headerRow.getCell(competitionDiaryDateColumn).setComponent(this.getDatePicker());
        headerRow.getCell(competitionDiaryPlaceColumn).setComponent(this.getTfSearchPlace());
        headerRow.getCell(competitionDiaryDisciplineColumn).setComponent(this.getTfSearchDiscipline());
    }

    private void initializeGridData(){

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;

        List<CompetitionDiaryEntry> competitionDiaryEntriesByUser = this.getCompetitionDiaryUiService().getCompetitionDiaryEntriesByUser(currentPersonDTO.getId());

        this.getCompetitionDiaryEntryBuffer().addAll(competitionDiaryEntriesByUser);

        this.competitionDiaryEntryInMemoryDataProvider = new ListDataProvider<>(this.getCompetitionDiaryEntryBuffer());

        this.getCompetitionDiaryEntryGrid().setItems(this.getCompetitionDiaryEntryInMemoryDataProvider());

    }

    private void initializeRootLayout(){

        this.componentRootLayout = new VerticalLayout();
        this.componentRootLayout.setSizeFull();

        this.getComponentRootLayout().add(this.getBtnAdd());
        this.getComponentRootLayout().add(this.getCompetitionDiaryEntryGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnAdd().addClickListener(doOnClickAdd());

        this.getCompetitionDiaryEntryGrid().addItemClickListener(doOnClickGrid());

    }

    private ComponentEventListener<ItemClickEvent<CompetitionDiaryEntry>> doOnClickGrid() {
        return itemClickevent -> {

            CompetitionDiaryEntry clickedEntry = itemClickevent.getItem();

            CompetitionDiaryGridClickEventRequest event = new CompetitionDiaryGridClickEventRequestImpl(clickedEntry);

            this.notifyEventListenerForClickedGrid(event);

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickAdd() {
        return event -> {

            this.notifyEventListenerForClickedAddButton();

        };
    }

    @Override
    public void addEventListeners(CompetitionDiaryGridEventListener listener) {
        this.getEventListeners().add(listener);
    }

    private void notifyEventListenerForClickedAddButton() {

        this.getEventListeners().forEach(listener -> listener.handleButtonAdd());

    }

    private void notifyEventListenerForClickedGrid(CompetitionDiaryGridClickEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleGridClick(event));

    }

    public void refreshGrid() {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;

        List<CompetitionDiaryEntry> trainingsDiaryEntryiesByUser = this.getCompetitionDiaryUiService()
                                                                        .getCompetitionDiaryEntriesByUser(currentPersonDTO.getId());

        this.getCompetitionDiaryEntryBuffer().clear();
        this.getCompetitionDiaryEntryBuffer().addAll(trainingsDiaryEntryiesByUser);

        this.getCompetitionDiaryEntryInMemoryDataProvider().refreshAll();
        this.getCompetitionDiaryEntryInMemoryDataProvider().clearFilters();

    }
}
