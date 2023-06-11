package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingsDiaryUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridClickedEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingdiary.event.traininfdiarygrid.TrainingsDiaryGridClickedEventRequestImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingDiaryGridComponent extends AbstractComponent implements AbstractObserver<TrainingsDiaryGridEventListener> {

    private VerticalLayout componentRootLayout;

    private Button btnAdd;

    private DatePicker datePicker;

    private Grid<TrainingDiaryEntry> trainingDiaryGrid;
    private List<TrainingDiaryEntry> trainingDiaryList;
    private InMemoryDataProvider<TrainingDiaryEntry> trainingDiaryEntryInMemoryDataProvider;

    private Set<TrainingsDiaryGridEventListener> eventListeners;

    private final TrainingsDiaryUiService trainingsDiaryUiService;

    private final SecurityService securityService;

    private TrainingDiaryEntry clickedRow;

    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.eventListeners = new HashSet<>();

        this.trainingDiaryList = new ArrayList<>();

    }

    @Override
    protected void initializeComponents() {

        if(this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE")) {

            this.initializeGrid();
            this.intitializeGridData();
            this.initializeComponentRootLayout();

        } else {

            this.initializeGrid();
            this.initializeComponentRootLayout();
        }

    }

    private void initializeGrid(){

        this.trainingDiaryGrid = new Grid<>();
        this.trainingDiaryGrid.addThemeVariants((GridVariant.LUMO_NO_BORDER));
        this.trainingDiaryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.trainingDiaryGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        Grid.Column<TrainingDiaryEntry> dateColumn = this.trainingDiaryGrid.addColumn(TrainingDiaryEntry::getDate).setHeader("Datum");
        Grid.Column<TrainingDiaryEntry> sessionColumn = this.trainingDiaryGrid.addColumn(TrainingDiaryEntry::getSession).setHeader("Einheit");
        Grid.Column<TrainingDiaryEntry> feelingColumn = this.trainingDiaryGrid.addColumn(TrainingDiaryEntry::getFeeling).setHeader("Gefühlszustand");

        this.btnAdd = new Button();
        this.btnAdd.setText("Hinzufügen");
        this.btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        this.datePicker = new DatePicker();
        this.datePicker.setLocale(Locale.GERMANY);
        this.datePicker.setClearButtonVisible(true);
        this.datePicker.setWidth("150px");

        HeaderRow headerRow = this.getTrainingDiaryGrid().appendHeaderRow();
        headerRow.getCell(dateColumn).setComponent(this.getDatePicker());

    }

    private void intitializeGridData(){

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;

        List<TrainingDiaryEntry> trainingsDiaryEntryiesByUser = this.getTrainingsDiaryUiService().getTrainingsDiaryEntriesByUser(currentPersonDTO.getId());

        this.getTrainingDiaryList().addAll(trainingsDiaryEntryiesByUser);

        this.trainingDiaryEntryInMemoryDataProvider = new ListDataProvider<>(this.getTrainingDiaryList());

        this.getTrainingDiaryGrid().setItems(this.getTrainingDiaryEntryInMemoryDataProvider());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();

        this.getComponentRootLayout().add(this.getBtnAdd());
        this.getComponentRootLayout().add(this.getTrainingDiaryGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getDatePicker().addValueChangeListener(doOnClickDate());

        this.getTrainingDiaryGrid().addItemClickListener(doOnClickGrid());

        this.getBtnAdd().addClickListener(doOnClickADd());

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickADd() {
        return event -> {

            this.notifyEventListenerForClickAddButton();

        };
    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickShare() {
        return buttonClickEvent -> {
            System.out.println("Klick test");
        };
    }

    private ComponentEventListener<ItemClickEvent<TrainingDiaryEntry>> doOnClickGrid() {
        return trainingDiaryEntryItemClickEvent -> {

            this.clickedRow = trainingDiaryEntryItemClickEvent.getItem();

            TrainingsDiaryGridClickedEventRequest event = new TrainingsDiaryGridClickedEventRequestImpl(clickedRow);

            this.notifyEventListenersForShowingForm(event);

        };
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> doOnClickDate() {
        return datePickerLocalDateComponentValueChangeEvent -> {

            LocalDate date = datePicker.getValue();
            if (date == null) {
                System.out.println("Feld ist leer");
            } else {
                System.out.println(date);
            }
        };
    }

    @Override
    public void addEventListeners(TrainingsDiaryGridEventListener listener){
        this.getEventListeners().add(listener);
    }


    private void notifyEventListenersForShowingForm(TrainingsDiaryGridClickedEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleClickGrid(event));

    }

    private void notifyEventListenerForClickAddButton() {

        this.getEventListeners().forEach(listener -> listener.handleClickAdd());
    }

    public void refreshGrid() {

        UserDetails authenticatedUser = this.getSecurityService().getAuthenticatedUser();
        AppUserDTO currentPersonDTO = (AppUserDTO) authenticatedUser;

        List<TrainingDiaryEntry> trainingsDiaryEntryiesByUser = this.getTrainingsDiaryUiService().getTrainingsDiaryEntriesByUser(currentPersonDTO.getId());

        this.getTrainingDiaryList().clear();
        this.getTrainingDiaryList().addAll(trainingsDiaryEntryiesByUser);

        this.getTrainingDiaryEntryInMemoryDataProvider().refreshAll();
        this.getTrainingDiaryEntryInMemoryDataProvider().clearFilters();

    }
}
