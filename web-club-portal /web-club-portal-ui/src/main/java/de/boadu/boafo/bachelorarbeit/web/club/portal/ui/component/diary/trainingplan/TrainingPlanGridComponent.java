package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.boadu.boafo.bachelorarbeit.web.club.portal.config.security.SecurityService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.TrainingPlanUiService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.AbstractObserver;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.RefreshableComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplangrid.TrainingPlanGridEventListener;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplangrid.TrainingPlanGridEventRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component.diary.trainingplan.event.trainingplangrid.TrainingPlanGridEventRequestImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@SpringComponent
@UIScope
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingPlanGridComponent extends AbstractComponent implements AbstractObserver<TrainingPlanGridEventListener>,
        RefreshableComponent<Collection<TrainingPlanEntry>> {

    private VerticalLayout componentRootLayout;

    private DatePicker trainingDate;

    private Button btnAdd;

    private Grid<TrainingPlanEntry> trainingPlanGrid;
    private List<TrainingPlanEntry> trainingPlanBuffer;
    private InMemoryDataProvider<TrainingPlanEntry> trainingPlanEntryInMemoryDataProvider;

    private Set<TrainingPlanGridEventListener> eventListeners;

    private final SecurityService securityService;

    private final TrainingPlanUiService trainingPlanUiService;


    @Override
    protected Component getRootLayout() {
        return this.getComponentRootLayout();
    }

    @Override
    protected void initializeInternalState() {

        this.trainingPlanBuffer = new ArrayList<>();

        this.eventListeners = new HashSet<>();

    }

    @Override
    protected void initializeComponents() {

        this.initializeGrid();
        this.initializeGridData();
        this.initializeComponentRootLayout();

    }

    private void initializeGrid(){

        this.trainingPlanGrid = new Grid<>();
        this.trainingPlanGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.trainingPlanGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        this.trainingPlanGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        Grid.Column<TrainingPlanEntry> dateColumn = this.trainingPlanGrid.addColumn(TrainingPlanEntry::getDate).setHeader("Datum");
        this.trainingPlanGrid.addColumn(TrainingPlanEntry::getSession).setHeader("Einheit");
        this.trainingPlanGrid.addColumn(TrainingPlanEntry::getAthlete).setHeader("Athlet");

        this.btnAdd = new Button();
        this.btnAdd.setText("Hinzufügen");
        this.btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        this.trainingDate = new DatePicker();
        this.trainingDate.setLocale(Locale.GERMANY);
        this.trainingDate.setClearButtonVisible(true);
        this.trainingDate.setWidth("150px");

        HeaderRow headerRow = this.getTrainingPlanGrid().appendHeaderRow();
        headerRow.getCell(dateColumn).setComponent(this.getTrainingDate());

    }

    private void initializeGridData(){

        Long userId = this.getSecurityService().getUserId();

        List<TrainingPlanEntry> trainingPlanEntriesByUser = this.getTrainingPlanUiService().getTrainingPlanEntriesByUser(userId);

        this.getTrainingPlanBuffer().addAll(trainingPlanEntriesByUser);

        this.trainingPlanEntryInMemoryDataProvider = new ListDataProvider<>(this.getTrainingPlanBuffer());

        this.getTrainingPlanGrid().setItems(this.getTrainingPlanEntryInMemoryDataProvider());

    }

    private void initializeComponentRootLayout(){

        this.componentRootLayout = new VerticalLayout();

        if(this.getSecurityService().getUserRoles().contains("ROLE_ATHLETE") &&
                !(this.getSecurityService().getUserRoles().contains("ROLE_TRAINER"))){
            this.getBtnAdd().setVisible(false);
        }

        this.getComponentRootLayout().add(this.getBtnAdd());
        this.getComponentRootLayout().add(this.getTrainingPlanGrid());

    }

    @Override
    protected void initializeComponentsActions() {

        this.getBtnAdd().addClickListener(doOnClickAdd());

        this.getTrainingPlanGrid().addItemClickListener(clickEvent -> {

            TrainingPlanEntry clickedEntry = clickEvent.getItem();

            TrainingPlanGridEventRequest event = new TrainingPlanGridEventRequestImpl(clickedEntry);

            this.notifyEventListenerForClickedGrid(event);

        });

    }

    private ComponentEventListener<ClickEvent<Button>> doOnClickAdd() {
        return event -> {

            this.notifyEventListenerForClickAddButton();

        };
    }

    @Override
    public void addEventListeners(TrainingPlanGridEventListener listener) {

        this.getEventListeners().add(listener);

    }

    private void notifyEventListenerForClickAddButton(){

        this.getEventListeners().forEach(listener -> listener.handleButtonAdd());

    }

    private void notifyEventListenerForClickedGrid(TrainingPlanGridEventRequest event) {

        this.getEventListeners().forEach(listener -> listener.handleGridClick(event));

    }



    @Override
    public void refreshGrid(Collection<TrainingPlanEntry> data) {

        this.getTrainingPlanBuffer().clear();
        this.getTrainingPlanBuffer().addAll(data);

        this.getTrainingPlanEntryInMemoryDataProvider().refreshAll();

    }

    @Override
    public void clearData() {

        this.getTrainingPlanBuffer().clear();

        this.getTrainingPlanEntryInMemoryDataProvider().refreshAll();
        this.getTrainingPlanEntryInMemoryDataProvider().clearFilters();

    }
}
